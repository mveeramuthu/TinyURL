import java.util.HashMap;

/**
 * Provides services to create a tiny URL with max. of 6 characters for any given URL.
 * Also to lookup a generated tiny URL and get the original URL. 
 * 
 * @author mveeramuthu
 *
 */
public class TinyURL {
	
	final String DOMAIN = "http://sm.ll\\";
	final String LONG_URL_POSSIBLE_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%^&*()_-+=|\\?<>,.:;\"\'/";
	final String TINY_URL_POSSIBLE_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_-";
	final int TINY_URL_LEN = 6;
	
	HashMap<Integer, String> mapIdToLongURL = new HashMap<Integer, String>();
	int nAutoIncrementedId = 25001;
	
	/**
	 * Create a tiny URL with max. of 6 characters for any given URL.
	 * @param Long URL
	 * @return Tiny URL
	 */
	public String createTinyURL(String sLongURL) {
				
		int nId = hashLongURLToId(sLongURL);
		String sTinyURL = DOMAIN + convertFromBase10(nId);
		
		return sTinyURL;
	}
	
	/**
	 * Look-up a tiny URL and get the Original URL.
	 * @param Tiny URL
	 * @return Long URL
	 */
	public String lookupTinyURL(String sTinyURL) {
		
		String sLongURL = null;
		
		StringBuilder sbTinyURL = new StringBuilder();
		sbTinyURL.append(sTinyURL.substring(DOMAIN.length()));
		
		int nId = convertToBase10(sbTinyURL.toString());
		
		if(mapIdToLongURL.containsKey(nId))
			sLongURL = mapIdToLongURL.get(nId);
		else
			sLongURL="\nSorry, The hash function we have used in this program is weak and hence unable "
					+ "to retrieve the proper long URL.";
		
		return sLongURL;
	}
	
	/**
	 * Create & update a hash table with Id to Long URL mapping.
	 *  
	 * Note: Ideally this method would Serialize the hashMap with a database.
	 * 
	 * @param Long URL
	 * @return Id
	 */
	private int hashLongURLToId(String nLongURL) {
		
		nAutoIncrementedId++;
		System.out.println(nAutoIncrementedId);		
		mapIdToLongURL.put(nAutoIncrementedId, nLongURL);
		
		return nAutoIncrementedId;
	}
	
	/**
	 * Convert base 10 number to the new base value which is
	 * a short URL.
	 *  
	 * @param Base 10 value
	 * @return New base value
	 */
	private String convertFromBase10(int nId) {
		
		StringBuilder sbNewBaseId = new StringBuilder();		
		int nNewBase = TINY_URL_POSSIBLE_CHARS.length();
		
		while (sbNewBaseId.length() < TINY_URL_LEN && nId > 0) {
			
			int nIndex = (int) (nId % nNewBase);			
			sbNewBaseId.append(TINY_URL_POSSIBLE_CHARS.charAt(nIndex));
			nId = nId / nNewBase;
        }
		
		return sbNewBaseId.toString();
	}
	
	/**
	 * Convert the new base value to base 10 number.
	 * 
	 * @param New Base value
	 * @return Base 10 value
	 */
	private int convertToBase10(String sNewBaseId) {
		
		int nBase10 = 0;
		
		for(int i=sNewBaseId.length()-1; i>=0; i--) {
			
			int nCurrValue = TINY_URL_POSSIBLE_CHARS.indexOf(sNewBaseId.charAt(i));			
			nBase10 += (nCurrValue * Math.pow(TINY_URL_POSSIBLE_CHARS.length(), i));			
		}
	
		return nBase10;
	}
	
	/**
	 * Driver method for temporary testing
	 * @param args
	 */
	public static void main(String[] args) {
		
		TinyURL obj = new TinyURL();
		
		String longURL = "https://www.groupon.com/deals/gg-salomon-sense-mantra-2-mens-running-shoes";
		String tinyURL = obj.createTinyURL(longURL);
		
		System.out.println("Tiny URL for " + longURL + " : " + tinyURL);	
		System.out.println("Long URL of " + tinyURL + " : " + obj.lookupTinyURL(tinyURL));
		
		
		longURL = "https://www.groupon.com/coupons/stores/personalizationmall.com?c=51a73f83-1b81-4c8d-962e-074b21dea74b";
		tinyURL = obj.createTinyURL(longURL);
		
		System.out.println("Tiny URL for " + longURL + " : " + tinyURL);	
		System.out.println("Long URL of " + tinyURL + " : " + obj.lookupTinyURL(tinyURL));
		
	}

}
