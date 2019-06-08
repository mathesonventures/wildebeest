package co.mv.wb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Config
{
	private static final Logger LOG = LoggerFactory.getLogger(Config.class);

	private Config()
	{
	}

	public static String getSettingString(String key, String defaultValue)
	{
		String result = System.getProperty(key);
		if (result == null)
		{
			LOG.debug(String.format("Using default %s: %s", key, defaultValue));
			result = defaultValue;
		}
		else
		{
			LOG.debug(String.format("Using provided %s: %s", key, result));
		}

		return result;
	}

	public static int getSettingInt(String key, int defaultValue)
	{
		int result;
		String raw = System.getProperty(key);
		if (raw == null)
		{
			LOG.debug(String.format("Using default %s: %d", key, defaultValue));
			result = defaultValue;
		}
		else
		{
			result = Integer.parseInt(raw);
			LOG.debug(String.format("Using provided %s: %d", key, result));
		}

		return result;
	}
}
