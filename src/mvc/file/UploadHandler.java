package mvc.file;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import mvc.controller.CommandHandler;

public class UploadHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (req.getMethod().equalsIgnoreCase("get")) {
			return "fileUploadForm.jsp";
		}else if(req.getMethod().equalsIgnoreCase("post")){
			return postProcess(req, res);
		}
		return null;
	}
	
	private String postProcess(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String uploadPath = req.getRealPath("upload"); //파일 들어갈 폴더 명(프로젝트 안 upload폴더)
		
		File dir = new File(uploadPath);
		if(dir.exists() == false){
			dir.mkdirs();
		}
		
		//upload시작
		try{
			int size = 1024 * 1024 * 10; //10MB
			
			MultipartRequest multi = new MultipartRequest(req, uploadPath, size, "utf-8", new DefaultFileRenamePolicy());
			
			Enumeration files = multi.getFileNames(); //실제 upload된 파일 정보
			ArrayList<String> arrFile = new ArrayList<>();
			
			String keyFile1 = "";
			String keyFile2 = "";
			String fileName1 = ""; //(DB에 올릴 때 파일은 이걸로)
			String fileName2 = "";
			String originFileName1 = "";
			String originFileName2 = "";
			
			keyFile1 = (String) files.nextElement(); //key1받음
			fileName1  = multi.getFilesystemName(keyFile1); //변경된 파일 명(DB에 올릴 때 파일은 이걸로)
			originFileName1 = multi.getOriginalFileName(keyFile1); //원래 파일 명
			/* 원래 이름이 bongi1 라면, 존재하는 파일이 있을 때 이름이 변경되어 bongi2 로 들어감*/
			
			if (fileName1 != null && !fileName1.isEmpty()) {
				arrFile.add(fileName1);
			}
			
			keyFile2 = (String) files.nextElement();
			fileName2  = multi.getFilesystemName(keyFile2);
			originFileName2 = multi.getOriginalFileName(keyFile2);
			
			if (fileName2 != null && !fileName2.isEmpty()) {
				arrFile.add(fileName2);
			}
			
			req.setAttribute("file", arrFile);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "fileUploadSuccess.jsp";
	}

}
