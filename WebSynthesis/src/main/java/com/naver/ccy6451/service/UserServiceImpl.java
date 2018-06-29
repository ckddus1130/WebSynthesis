package com.naver.ccy6451.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.naver.ccy6451.dao.UserDao;
import com.naver.ccy6451.domain.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public String emailcheck(HttpServletRequest request) {
		// 작업은 서비스에서 하니까 서비스에서 파라미터 읽기
		String email = request.getParameter("email");
		// Dao의 메소드를 호출해서 결과를 전송
		return userDao.emailcheck(email);
	}

	// 실제적인 작업을 하는 것은 Service에서 합니다.
	@Override
	public void register(MultipartHttpServletRequest request) {
		// 1. 제일 먼저 파라미터 읽기
		String email = request.getParameter("email");
		String pw = request.getParameter("pw");
		String nickname = request.getParameter("nickname");
		// 위처럼 문자열은 String을 읽지만 파일은 아래와 같은방식으로
		// 읽습니다.
		MultipartFile image = request.getFile("image");
		// 파일을 저장할 경로를 만들기
		// 파일은 절대경로로만 저장이 가능
		// 프로젝트 내의 userimage 디렉토리의 절대경로를 만들기
		String uploadPath = request.getRealPath("/userimage");
		// 랜덤한 64자리의 문자열 만들기
		UUID uid = UUID.randomUUID();
		// 원본 파일이름 가져오기
		String filename = image.getOriginalFilename();
		filename = uid + "_" + filename;
		// 업로드할 파일의 실제 경로 만들기
		String filepath = uploadPath + "\\" + filename;

		// Dao의 파라미터로 사용할 객체
		User user = new User();
		// 업로드할 파일의 실제 경로 만들기
		File f = new File(filepath);
		try {
			// 파일 전송 - 파일 업로드
			image.transferTo(f);
			// Dao의 파라미터 만들기
			user.setEmail(email);
			user.setPw(BCrypt.hashpw(pw, BCrypt.gensalt(10)));
			user.setNickname(nickname);
			// 데이터베이스에는 파일 이름만 저장
			user.setImage(filename);
			userDao.register(user);
		} catch (Exception e) {
			System.out.println("회원가입 실패:" + e.getMessage());
		}
	}

	@Override
	public User login(HttpServletRequest request) {
		// 파라미터읽기
		String email = request.getParameter("email");
		String pw = request.getParameter("pw");

		// Dao 메소드 호출
		User user = userDao.login(email);
		if (user != null) {
			// 비밀번호가 일치하면
			if (BCrypt.checkpw(pw, user.getPw()) == true) {
				// 비밀번호만 초기화
				user.setPw("");
			}
			// 비밀번호가 일치하지 않으면 전부 초기화
			else {
				user = null;
			}
		}
		return user;
	}

	@Override
	public String address(String loc) {
		String[] ar = loc.split("-");
		String addr = "https://dapi.kakao.com/v2/local/geo/coord2regioncode.json?";
		addr = addr + "x=" + ar[1] + "&y=" + ar[0];

		try {
			URL url = new URL(addr);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			// 인증받기
			con.setRequestProperty("Authorization", "KakaoAK f767f29740a04c38b732dabe1839d6f4");
			con.setConnectTimeout(20000);
			con.setUseCaches(false);
			// 줄 단위 데이터 읽기
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			// 문자열을 임시로 저장할 객체 만들기
			StringBuilder sb = new StringBuilder();
			while (true) {
				// 한 줄의 데이터 읽기
				String line = br.readLine();
				// 읽은 데이터가 없으면 반복문 종료
				if (line == null) {
					break;
				}
				// 읽은 데이터가 있으면 sb에 추가
				sb.append(line);
			}
			// 연결 해제
			br.close();
			con.disconnect();
			// JSONObject를 생성
			JSONObject obj = new JSONObject(sb.toString());
			// System.out.println(obj);
			JSONArray imsi = obj.getJSONArray("documents");
			// System.out.println(imsi);
			JSONObject o = imsi.getJSONObject(0);
			String address = o.getString("address_name");
			System.out.println(address);
			return address;
		} catch (Exception e) {
			System.out.println("주소가져오기 실패:" + e.getMessage());

		}
		return null;
	}

	@Override
	public Map<String, Object> traffic() {
		Map<String, Object> map = new HashMap<String, Object>();

		// 텍스트 파일의 내용 읽기 문자열이고 읽는다를보면 바로 BufferedReader
		// 쓸 때는 printwriter 애들
		try {
			// 텍스트 파일의 내용 읽기 -BufferedReader
			BufferedReader br = new BufferedReader(
					new FileReader(
							"D:\\csvfile\\java\\log.txt"));
			// 파일의 내용을 전부 읽어서 출력
			while (true) {
				String line = br.readLine();
				//한 줄씩 읽었는데 이제 읽을 게 없다면 break!
				if (line == null) {
					break;
				}
				// 공백을 가지고 문자열을 분할해서 ar에 저장
				// ar[0]이 ip이고 ar[9]이 트래픽
				String[] ar = line.split(" ");
				
				Object traffic = map.get("ip");
				// ip가 존재하지 않은 경우
				// "-" 안해주면 데이터를 출력하지 못합니다.
				if (traffic == null && !ar[9].equals("-")) {
					//존재하지 않으면 저장해주면 됩니다.
					//트래픽 합계를 구하기위해서 변환해서 저장해줍니다.
					//저장: 없을 때는 값을 정수로 변환해서 저장
					map.put(ar[0],Integer.parseInt(ar[9]));
				}
				// ip가 존재하는 경우
				else  if(!ar[9].equals("-")) {
					Integer imsi = (Integer)traffic;
					imsi = imsi + Integer.parseInt(ar[9]);
					map.put(ar[0],imsi);
				}
				
				// System.out.println(ar[0] + ":" + ar[9]);
				// System.out.println(line);
				// IP 별 합계를 구해야 하는데 중복이 있을 수 있기때문에
				// 자료구조인 Set이나 Map을 생각해 내야 합니다.

			}
			br.close();
		}

		catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return map;
	}

	@Override
	public List<List<Object>> crimeratio() {
		List<List<Object>> data = new ArrayList<List<Object>>();
		String[] ar = null;
		try {
			//2016년 범죄율 데이터를 가져오기
			BufferedReader br = new BufferedReader(
					new InputStreamReader(
							new FileInputStream("D:\\csvfile\\java\\2016crime_log.csv")));

			String csv = "";
			StringBuilder sb = new StringBuilder();
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				sb.append(line + ",");
			}
			csv = sb.toString();
			ar = csv.split(",");
			List<String> list = new ArrayList<String>();
			int flag = 0;
			String imsi = "";
			String result = "";
			for (int i = 0; i < ar.length; i++) {
				// 순서대로 0번부터 데이터를 가져오기
				imsi = ar[i];

				//System.out.println("확인체크:" + imsi);
				int a = imsi.indexOf("\"");
				System.out.println("위치" + a);
				//System.out.println(flag);
			}
			if (flag == 0 && imsi.indexOf("\"") >= 0) {
				flag = 1;
				result = imsi;
				//System.out.println("if안에:" + imsi);
			} else {
				if (flag == 1 && imsi.indexOf("\"") >= 0) {
					flag = 0;
					result = result + imsi;
					System.out.println("");
				}
			}
		}

		catch (Exception e) {
			System.out.println("파일가져오기 에러:" + e.getMessage());
			e.printStackTrace();
		}

		List<Object> list1 = new ArrayList<Object>();
		//강력범죄
		list1.add(ar[0]);
		//생활정도(계)
		list1.add(ar[2]);
		//생활정도(하)
		list1.add(ar[3]);
		//생활정도(중)
		list1.add(ar[4]);
		//생활정도(상)
		list1.add(ar[5]);
		
		data.add(list1);

		System.out.println(list1);
		return data;

	}

}
