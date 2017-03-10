package net.xssu.ipm.exception;

/**
 * @author bonult
 * @description TODO
 * @version v1.0
 * @date 2016年11月25日 下午12:58:09
 */
public class MyTransactionException extends RuntimeException {

	private static final long serialVersionUID = -2985233557035253721L;

	public MyTransactionException() {
		super();
	}

	public MyTransactionException(String message) {
		super(message);
	}

	public MyTransactionException(String message, Throwable cause) {
		super(message, cause);
	}

}
