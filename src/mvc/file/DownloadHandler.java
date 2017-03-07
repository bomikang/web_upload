package mvc.file;

import java.io.FileInputStream;
import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.controller.CommandHandler;

public class DownloadHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String fileName = req.getParameter("filename");
		String uploadPath = req.getRealPath("upload");
		String sFilePath = uploadPath + "\\" + fileName; //실제 파일 경로
		
		byte b[] = new byte[4096]; //4KB
		FileInputStream in = new FileInputStream(sFilePath);
		String sMimeType = req.getServletContext().getMimeType(sFilePath);
		System.out.println("sMimeType : " + sMimeType);
		
		if (sMimeType == null) {
			//octet-stream은 8비트로 된 일련의 데이터
			//지정되지 않은 파일 형식
			sMimeType = "application/octet-stream";
		}
		
		res.setContentType(sMimeType);
		
		//한글 깨지지 않도록
		String sEncoding = URLEncoder.encode(fileName, "utf-8");

		//브라우저에 다운 파일 인식
		res.setHeader("Content-Disposition", "attachment; filename=" + sEncoding);
		ServletOutputStream out = res.getOutputStream();
		
		//파일 데이터 읽어오기
		int numRead;
		while ( (numRead = in.read(b, 0, b.length)) != -1) {
			out.write(b, 0, numRead);
		}
		
		out.flush();
		out.close();
		in.close();
		return null;
	}

}
