package server;

import java.util.ArrayList;

/**
 * classe che estende ArrayList<String>
 *
 */
public class TopicArray extends ArrayList<String>{


	private static final long serialVersionUID = 1L;

	@Override
	public String toString ()	{
		String ret = "";
		for (String s : this)	{
			ret+=s + "\n";
		}

		return ret;
	}

}
