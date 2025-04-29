package com.saar.blog.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.saar.blog.service.FileService;

@Service
public class FileServiceImpll implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		// file.getOriginalFilename() से फाइल का ओरिजिनल नाम प्राप्त होता है।
		//उदाहरण: abc.png
		String name=file.getOriginalFilename();
		//abc.png
		
		//random namegenerate file
		//UUID.randomUUID().toString() एक यूनिक आईडी बनाता है।
//		फिर इस यूनिक आईडी को फाइल के एक्सटेंशन (जैसे .png) के साथ जोड़ते हैं।
//		उदाहरण: 12345-abc.png
		
		String randomID=UUID.randomUUID().toString();
		String fileName1=randomID.concat(name.substring(name.lastIndexOf(".")));
		
		
		//Full Path
		//path + File.separator + fileName1 के जरिए फाइल का पूरा पाथ तैयार किया जाता है।
//		उदाहरण: C:/images/12345-abc.png
		String filePath=path+File.separator+fileName1;
		
		// create folder if not created
//		new File(path) के जरिए फोल्डर का ऑब्जेक्ट बनता है।
//		अगर फोल्डर मौजूद नहीं है (!f.exists()), तो f.mkdir() से नया फोल्डर बनाते हैं।
		File f=new File(path);
		if(!f.exists())
		{
			f.mkdir();
		}
		
		// File copy
//		Files.copy(file.getInputStream(), Paths.get(filePath)) से फाइल को दिए गए पाथ पर सेव किया जाता है।
		Files.copy(file.getInputStream(), Paths.get(filePath));
		return fileName1;//अंत में, फाइल का यूनिक नाम (fileName1) वापस किया जाता है।
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		//path + File.separator + fileName से फाइल का पूरा पाथ तैयार होता है।
//		उदाहरण: C:/images/12345-abc.png
		String fullPath=path+File.separator+fileName;
		//new FileInputStream(fullPath) से फाइल को पढ़ने के लिए एक स्ट्रीम बनाई जाती है।
		InputStream is=new FileInputStream(fullPath);
		
		// db logic to return inputstream
		
		return is;
	}

}












