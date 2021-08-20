package com.ssafy.commb.service;

import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public interface S3Service {
    public void deleteS3(String url, String dir);
    public void deleteS3(List<String> urls, String dir);

    public String uploadS3(Part part, String dir) throws IOException;

    public Part extractFile(Collection<Part> parts) throws IOException;

    public String getFileName(Part part);
}
