package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * Created by admin on 2016/9/28.
 */
public class JsonUtils {
	private final static ObjectMapper mapper = new ObjectMapper();
	private final static Pattern arrayIndexPtn = Pattern.compile("^\\S([\\d+])$");

	public static boolean hasKey(String json, String key) {
		if (null != json && null != key) {
			try {
				JsonNode jsonNode = mapper.readTree(json);
				String[] paths = key.split("/");
				for (String path : paths) {
					jsonNode = jsonNode.path(path);
					System.out.println("jsonNode:" + jsonNode);
					if (jsonNode.isMissingNode())
						return false;
				}

				return true;
			} catch (IOException e) {
				throw new IllegalArgumentException(e);
			}
		} else {
			return false;
		}
	}

	public static void main(String[] args) throws Exception {
		String json = "{  \n" + "  \"country_id\" : \"China\",  \n" + "  \"birthDate\" : \"1949-10-01\",  \n" + "  \"nation\" : [ \"Han\", \"Meng\", \"Hui\", \"WeiWuEr\", \"Zang\" ],  \n" + "  \"lakes\" : [ \"Qinghai Lake\", \"Poyang Lake\", \"Dongting Lake\", \"Taihu Lake\" ],  \n" + "  \"provinces\" : [ {  \n" + "    \"name\" : \"Shanxi\",  \n" + "    \"population\" : 37751200  \n" + "  }, {  \n" + "    \"name\" : \"ZheJiang\",  \n" + "    \"population\" : 55080000  \n" + "  } ],  \n" + "  \"traffic\" : {  \n" + "    \"HighWay(KM)\" : 4240000,  \n" + "    \"Train(KM)\" : 112000  \n" + "  }  \n" + "}";
		//JsonNode jsonNode = JsonUtils.hasKey(json, "country_id");
		System.out.println(JsonUtils.hasKey(json, "traffic/Train(KM)"));
		Date date = new Date(1477752945000L);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(sdf.format(date));
	}
}
