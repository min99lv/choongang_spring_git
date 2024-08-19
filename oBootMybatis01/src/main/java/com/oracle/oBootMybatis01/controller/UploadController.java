package com.oracle.oBootMybatis01.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class UploadController {
	// upLoadForm 시작 화면
	@RequestMapping(value = "upLoadFormStart")
	public String upLoadFormStart(Model model) {
		System.out.println("upLoadFormStart start!!");
		return "upLoadFormStart";
	}

	@RequestMapping(value = "uploadForm", method = RequestMethod.GET)
	public void uploadForm() {
		System.out.println("upLoadForm get Start...");
		System.out.println();

	}

	@RequestMapping(value = "uploadForm", method = RequestMethod.POST)
	public String uploadForm(HttpServletRequest request, Model model) throws IOException, Exception {

		Part image = request.getPart("file1");
		InputStream inputStream = image.getInputStream();

		// 파일 확장자 구하기
		String fileName = image.getSubmittedFileName();
		String[] split = fileName.split("\\.");
		String originalName = split[split.length - 2];
		String suffix = split[split.length - 1];

		System.out.println("fileName___::" + fileName);
		System.out.println("originalName___::" + originalName);
		System.out.println("suffix___::" + suffix);

		// servlet을 상속 받지 못했을 때 realPath 불러 오는 방법
		String uploadPath = request.getSession().getServletContext().getRealPath("/upload/");

		System.out.println("uploadForm POST Start...");
		String savedName = uploadFile(originalName, inputStream, uploadPath, suffix);
		// Service --> DB URUD
		log.info("Return savedName: " + savedName);
		model.addAttribute("savedName", savedName);

		return "uploadResult";

	}

	// 같은 클래스 안에서만 호출하는 메서드 자동으로 생성됨
	private String uploadFile(String originalName, InputStream inputStream, String uploadPath, String suffix)
			throws FileNotFoundException, IOException {

		// universally unique indentifier (UUID) : 세계적으로 유일한 식별자를 돌려줌 랜덤하게
		UUID uid = UUID.randomUUID();
		// requestPath = requestPath + "resources/images";
		System.out.println("uploadPath__::" + uploadPath);
		// Directory 생성
		File fileDrectory = new File(uploadPath);
		if (!fileDrectory.exists()) {
			// 신규 폴더 생성
			fileDrectory.mkdir();
			System.out.println("업로드용 폴더 생성__::" + uploadPath);

		}
		String savedName = uid.toString() + "_" + originalName + "." + suffix;
		log.info("savedName" + savedName);
		// 임시 파일 생성
		File tempFile = new File(uploadPath + savedName);
		// ----------------------------------------------------------
		// backup file
		File tempFile3 = new File("c:/BACKUP/" + savedName);
		FileOutputStream outputStream3 = new FileOutputStream(tempFile3);
		// ----------------------------------------------------------

		// 생성된 임시파일에 요청으로 넘어온 file의 inputStream복사
		try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
			int read;
			byte[] bytes = new byte[2048000];
			// -1 : 파일이 끝날때까지
			while ((read = inputStream.read(bytes)) != -1) {
				// target file에 요청으로 넘어온 file의 inputstream 복사
				outputStream.write(bytes, 0, read);
				// backup파일에 요청으로 넘어온 file의 inputStream 복사
				// outputStream3.write(bytes,0,read);
				outputStream3.write(bytes, 0, read);
			}

		} finally {
			System.out.println("UpLoad The End....");
		}
		outputStream3.close();
		return savedName;
	}

	@RequestMapping(value = "uploadFileDelete", method = RequestMethod.GET)
	public String uploadFileDelete(HttpServletRequest request, Model model) {
		String uploadPath = request.getSession().getServletContext().getRealPath("/upload/");
		String uploadPath3 = ("c:/BACKUP/");
		String delFile = request.getParameter("delFile");
		System.out.println("uploadFileDelete Get Start!!");
		String deleteFile = uploadPath + delFile;
		String deleteFile3 = uploadPath3 + delFile;
		System.out.println("uploadFileDelete deleteFile__>>" + deleteFile);

		int delResult = upFileDelete(deleteFile);
		int delResult3 = upFileDelete(deleteFile3);
		
		model.addAttribute("deleteFile", deleteFile);
		model.addAttribute("delResult", delResult);

		return "uploadResult";
	}

	private int upFileDelete(String deleteFileName) {
		int result = 0;
		log.info("upFileDelete Result->>" + deleteFileName);
		File file = new File(deleteFileName);
		if (file.exists()) {
			if (file.delete()) {
				System.out.println("파일 삭제 성공");
				result = 1;
			} else {
				System.out.println("파일 삭제 실패");
				result = 0;
			}
		} else {
			System.out.println("삭제할 파일이 존재하지 않습니다.");
			result = -1;
		}

		return result;
	}

}
