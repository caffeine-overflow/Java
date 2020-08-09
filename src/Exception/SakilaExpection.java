/**
 * Program Name	: SakilaExpection.java
 * Purpose			: A custom exception class for the project
 * Author				: Prabin Gyawali (0877282)
 * Date					: Aug. 4, 2020
 */
package Exception;

import java.util.List;

@SuppressWarnings("serial")
public class SakilaExpection extends Exception
{
	public SakilaExpection() {
		super();
	}

	public SakilaExpection(String message) {
		super(message);
	}

	public SakilaExpection(List<String> messages) throws SakilaExpection {
		String combinedMessage="";
		for(String message:messages) combinedMessage+=message+'\n';
		throw new SakilaExpection(combinedMessage);

	}

	public SakilaExpection(String message, Throwable cause) {
		super(message, cause);
	}


}
