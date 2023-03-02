package org.hyl.cloudnote.util;

import java.security.MessageDigest;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;

public class NoteUtil {
	/**
	 * 密码加密处理
	 * @param src 明文
	 * @return 密文
	 * @throws Exception 
	 */
	public static String md5(String src) throws Exception{
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] input = src.getBytes();
		byte[] output = md.digest(input);
		//将output转成字符串
		//String dest = String.valueOf(output);//String dest = new String(output);//会有乱码
		//采用Base64将output转成字符串
		String dest = Base64.encodeBase64String(output);
		return dest;
	}
	/**
	 * 生成表的主键ID
	 * @return利用UUID产生一个字符串值
	 */
	public static String createId(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString();//128byte
	}
	public static void main(String[] args) throws Exception {
		System.out.println("a:"+NoteUtil.md5("123456"));
		System.out.println(createId());
		System.out.println(createId());
	}
}
