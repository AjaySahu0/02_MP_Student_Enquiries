package com.ait.utility;

import org.apache.commons.lang3.RandomStringUtils;

public class pwdUtils {

	public static String generateRandomPwd() {

		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		String pwd = RandomStringUtils.random(6, characters);

		return pwd;

	}
}
