package com.mannavoca.zenga.common.consts;

/**
 * 셔플 기준을 판단하는 상수 클래스
 */
public final class ShuffleEstimationConstants {
	private ShuffleEstimationConstants() {
	}

	public static final int MIN_LIMIT = 8;
	public static final int MID_LIMIT_1 = 12;
	public static final int MID_LIMIT_2 = 16;
	public static final int MAX_LIMIT = 20;
	public static final int THRESHOLD_1 = 25;
	public static final int THRESHOLD_2 = 60;
	public static final int THRESHOLD_3 = 100;

	/**
	 * 멤버 수에 따라 셔플 제한을 결정한다.
	 * @param memberCount 채널의 멤버 수
	 * @return int 셔플 제한 횟수
	 */
	public static int determineLimit(Long memberCount) {
		if (memberCount >= THRESHOLD_3) {
			return MAX_LIMIT;
		} else if (memberCount >= THRESHOLD_2) {
			return MID_LIMIT_2;
		} else if (memberCount >= THRESHOLD_1) {
			return MID_LIMIT_1;
		} else {
			return MIN_LIMIT;
		}
	}

	public static int determineLimitShuffleCount(Long memberCount) {
		if (memberCount >= THRESHOLD_3) {
			return MAX_LIMIT/4;
		} else if (memberCount >= THRESHOLD_2) {
			return MID_LIMIT_2/4;
		} else if (memberCount >= THRESHOLD_1) {
			return MID_LIMIT_1/4;
		} else {
			return MIN_LIMIT/4;
		}
	}
}
