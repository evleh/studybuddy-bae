package at.technikum.studybuddy.dto;

import java.io.InputStream;

public record FileDownload(InputStream stream, String contentType){}
