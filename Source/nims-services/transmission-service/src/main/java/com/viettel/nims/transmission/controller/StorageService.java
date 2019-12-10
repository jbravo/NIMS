package com.viettel.nims.transmission.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

public class StorageService {

	Logger log = LoggerFactory.getLogger(this.getClass().getName());
	private final Path rootLocation = Paths.get("./upload-dir/");

	public String store(MultipartFile file, String stCode) {
		if (!Files.isDirectory(rootLocation)) {
			try {
				Files.createDirectory(rootLocation);
			} catch (IOException e) {
				throw new RuntimeException("Could not initialize storage!");
			}
		}
		try {
			String t = file.getOriginalFilename();
			String newName = t.substring(0, t.lastIndexOf(".")) + stCode + t.substring(t.lastIndexOf("."));
			Files.copy(file.getInputStream(), this.rootLocation
					.resolve(newName));
			return newName;
		} catch (Exception e) {
//			throw new RuntimeException("FAIL!");
			return "";
		}
	}

	public Resource loadFile(String filename) {
		try {
			Path file = rootLocation.resolve(filename);
			System.out.println(file.toString());
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("FAIL!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("FAIL!");
		}
	}

	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	public void init() {
		try {
			Files.createDirectory(rootLocation);
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize storage!");
		}
	}
}
