package okhttp3.test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import okio.BufferedSource;
import okio.Okio;

public class Main2 {

	private static final int DEFAULT_ENTITY_RETURN_MAX_LENGTH = Integer.MAX_VALUE;

	public byte[] post(String url) throws IOException {
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet httpPost = new HttpGet(url);
		httpPost.setHeader("Accept", "application/granbluefantasy");
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpPost);
			HttpEntity responseEntity = response.getEntity();
			return EntityUtils.toByteArray(responseEntity);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 释放资源
				if (httpClient != null) {
					httpClient.close();
				}
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;

	}

	public static Item readOneItem(BufferedSource bs) throws IOException {
		byte b = bs.readByte();
		if (getHeight4(b) == 0) {
			return new Item("NULL", "0", "0", "0");
		} else if (getHeight4(b) == 1) {
			return new Item("byte", String.valueOf(getLow4(b)), "1", "0");
		} else if (getHeight4(b) == 8) {
			return new Item("null", String.valueOf(getLow4(b)), "8", "0");
		} else if (getHeight4(b) == 10) {
			return new Item("text", bs.readUtf8(getLow4(b)), "10", "0");
		} else if (getHeight4(b) == 11) {
			return new Item("text", bs.readUtf8(getLow4(b) + 16), "11", "0");
		} else if (getHeight4(b) == 12 && getLow4(b) == 0) {
			return new Item("int", "0", "12", "0");
		} else if (getHeight4(b) == 12 && getLow4(b) == 2) {
			return new Item("int", "0", "12", "2");
		} else if (getHeight4(b) == 12 && getLow4(b) == 3) {
			return new Item("int", "0", "12", "3");
		} else if (getHeight4(b) == 12 && getLow4(b) == 11) {
			return new Item("int", String.valueOf(bs.readLong()), "12", "11");
		} else if (getHeight4(b) == 12 && getLow4(b) == 12) {
			return new Item("int", String.valueOf(bs.readByte()), "12", "12");
		} else if (getHeight4(b) == 12 && getLow4(b) == 13) {
			return new Item("int", String.valueOf(bs.readShort()), "12", "13");
		} else if (getHeight4(b) == 12 && getLow4(b) == 14) {
			return new Item("int", String.valueOf(bs.readInt()), "12", "14");
		} else if (getHeight4(b) == 13 && getLow4(b) == 6) {
			int cnt = (bs.readByte() & 0xFF);
			System.out.println("cnt = "+cnt);
			return new Item("date", stampToDate(bs.readInt()), "13", "6");
		} else if (getHeight4(b) == 13 && getLow4(b) == 9) {
			int cnt = (bs.readByte() & 0xFF);
			return new Item("text", bs.readUtf8(cnt), "13", "9");
		} else if (getHeight4(b) == 13 && getLow4(b) == 12) {
			return new Item("int", String.valueOf(bs.readShort()), "13", "12");
		} else if (getHeight4(b) == 13 && getLow4(b) == 14) {
			return new Item("int", String.valueOf(bs.readShort()), "13", "14");
		} else {
			System.out.println(getHeight4(b) + "," + getLow4(b));
			return new Item("error", "", String.valueOf(getHeight4(b)), String.valueOf(getLow4(b)));
		}

	}

	public static String stampToDate(int time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time_Date = sdf.format(new Date(time * 1000L));
		return time_Date;
	}

	public static int getHeight4(byte data) {
		int height;
		height = ((data & 0xf0) >> 4);
		return height;
	}

	public static int getLow4(byte data) {
		int low;
		low = (data & 0x0f);
		return low;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Main2 example = new Main2();
		String json = "https://beta.ceve-market.org/app/list/new"; // 
		int cnt = 0;
		try {
			BufferedSource bufferedFileSource = Okio.buffer(Okio.source(new ByteArrayInputStream(example.post(json))));
			for (int i = 0; i <= 117 * 1; i++) {
				Item item = readOneItem(bufferedFileSource);
				if (item.getValue().equals("KillID")) {
					cnt++;
				}
				System.out.println(item);
			}

			System.out.println();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("cnt = " + cnt);
	}

	private static byte[] listTobyte1(List<Byte> list) {
		if (list == null || list.size() < 0)
			return null;
		byte[] bytes = new byte[list.size()];
		int i = 0;
		Iterator<Byte> iterator = list.iterator();
		while (iterator.hasNext()) {
			bytes[i] = iterator.next();
			i++;
		}
		return bytes;
	}

	public static int toShort(byte bytes0, byte bytes1) {
		int number = 0;
		number += bytes0 << 0 * 8;
		number += bytes1 << 1 * 8;
		return number;
	}

	public static int toInt(byte bytes0, byte bytes1, byte bytes2, byte bytes3) {
		int number = 0;
		number += bytes0 << 0 * 8;
		number += bytes1 << 1 * 8;
		number += bytes2 << 2 * 8;
		number += bytes3 << 3 * 8;
		return number;
	}

}
