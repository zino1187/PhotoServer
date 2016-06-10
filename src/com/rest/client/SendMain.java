package com.rest.client;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;

public class SendMain {
	public SendMain() {
		String param = "value";
		String charset = "utf-8";
		File binaryFile = new File("D:/jsp_workspace/PhotoServer/WebContent/data/Jellyfish.jpg");
		String boundary = Long.toHexString(System.currentTimeMillis());
		String CRLF = "\r\n"; // Line separator required by multipart/form-data.
		HttpURLConnection con;

		try {
			URL url = new URL("http://192.168.43.30:9090/rest/gallery");
			con = (HttpURLConnection) url.openConnection();

			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
			con.setRequestMethod("POST");
			con.setRequestProperty("Connection", "keep-alive");
			con.setRequestProperty("Cache-Control", "max-age=0");
			con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

			OutputStream output = con.getOutputStream();

			con.connect();

			PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true);
			// 파라미터 정보
			writer.append("--" + boundary).append(CRLF);
			writer.append("Content-Disposition: form-data; name=\"title\"").append(CRLF);
			writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
			writer.append(CRLF).append("이 사진 멋지죠?").append(CRLF).flush();

			// Send binary file.
			writer.append("--" + boundary).append(CRLF);
			writer.append(
					"Content-Disposition: form-data; name=\"myFile\"; filename=\"" + binaryFile.getName() + "\"")
					.append(CRLF);
			writer.append("Content-Type: " + HttpURLConnection.guessContentTypeFromName(binaryFile.getName()))
					.append(CRLF);
			writer.append("Content-Transfer-Encoding: binary").append(CRLF);
			writer.append(CRLF).flush();
			
			Files.copy(binaryFile.toPath(), output);
			
			output.flush(); // Important before continuing with writer!
			writer.append(CRLF).flush(); // CRLF is important! It indicates end
											// of boundary.

			// End of multipart/form-data.
			writer.append("--" + boundary + "--").append(CRLF).flush();
			writer.close();

			int code = 0;
			code = con.getResponseCode();
			// System.out.println(code);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new SendMain();
	}

}