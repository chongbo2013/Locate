package com.example.locate;


import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;


public class Utils
{
	
	/** ����ƴ����ʽ�������� */
	private static HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
	
	/** 
	* ��ȡ�ַ��ڵ����к��ֵĺ���ƴ�� 
	* @param src 
	* @return 
	*/
	public static String chinese2pinyin(
			String src )
	{
		format.setCaseType( HanyuPinyinCaseType.LOWERCASE ); // Сдƴ����ĸ  
		format.setToneType( HanyuPinyinToneType.WITHOUT_TONE ); // ���������ʶ  
		format.setVCharType( HanyuPinyinVCharType.WITH_V ); // u:����ĸ�滻Ϊv  
		StringBuffer sb = new StringBuffer();
		int strLength = src.length();
		try
		{
			for( int i = 0 ; i < strLength ; i++ )
			{
				// ��Ӣ����ĸ�Ĵ��?Сд��ĸת��Ϊ��д����д��ֱ�ӷ���  
				char ch = src.charAt( i );
				if( ch >= 'a' && ch <= 'z' )
					sb.append( (char)( ch - 'a' + 'A' ) );
				if( ch >= 'A' && ch <= 'Z' )
					sb.append( ch );
				// �Ժ���Ĵ���  
				String[] arr = PinyinHelper.toHanyuPinyinStringArray( ch , format );
				if( arr != null && arr.length > 0 )
					sb.append( arr[0] ).append( " " );
			}
		}
		catch( BadHanyuPinyinOutputFormatCombination e )
		{
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	/** 
	 * ��ȡ�ַ��ڵ����к��ֵĺ���ƴ������дÿ���ֵ�����ĸ 
	 * @param src 
	 * @return 
	 */
	public static String spellWithTone(
			String src )
	{
		format.setCaseType( HanyuPinyinCaseType.LOWERCASE );// Сд  
		format.setToneType( HanyuPinyinToneType.WITH_TONE_MARK );// �����  
		format.setVCharType( HanyuPinyinVCharType.WITH_U_UNICODE );// u:����ĸ  
		if( src == null )
		{
			return null;
		}
		try
		{
			StringBuilder sb = new StringBuilder();
			for( int i = 0 ; i < src.length() ; i++ )
			{
				// ��Ӣ����ĸ�Ĵ��?Сд��ĸת��Ϊ��д����д��ֱ�ӷ���  
				char ch = src.charAt( i );
				if( ch >= 'a' && ch <= 'z' )
					sb.append( (char)( ch - 'a' + 'A' ) );
				if( ch >= 'A' && ch <= 'Z' )
					sb.append( ch );
				// �Ժ���Ĵ���  
				String[] arr = PinyinHelper.toHanyuPinyinStringArray( ch , format );
				if( arr == null || arr.length == 0 )
				{
					continue;
				}
				String s = arr[0];// ���ܶ�����,ֻȡ��һ��  
				char c = s.charAt( 0 );// ��д��һ����ĸ  
				String pinyin = String.valueOf( c ).toUpperCase().concat( s.substring( 1 ) );
				sb.append( pinyin ).append( " " );
			}
			return sb.toString();
		}
		catch( BadHanyuPinyinOutputFormatCombination e )
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/** 
	 * ��ȡ�ַ��ڵ����к��ֵĺ���ƴ������дÿ���ֵ�����ĸ 
	 * @param src 
	 * @return 
	 */
	public static String spellNoneTone(
			String src )
	{
		format.setCaseType( HanyuPinyinCaseType.LOWERCASE );// Сд  
		format.setToneType( HanyuPinyinToneType.WITHOUT_TONE );// �����  
		format.setVCharType( HanyuPinyinVCharType.WITH_U_UNICODE );// u:����ĸ  
		if( src == null )
		{
			return null;
		}
		try
		{
			StringBuilder sb = new StringBuilder();
			for( int i = 0 ; i < src.length() ; i++ )
			{
				// ��Ӣ����ĸ�Ĵ��?Сд��ĸת��Ϊ��д����д��ֱ�ӷ���  
				char ch = src.charAt( i );
				if( ch >= 'a' && ch <= 'z' )
					sb.append( (char)( ch - 'a' + 'A' ) );
				if( ch >= 'A' && ch <= 'Z' )
					sb.append( ch );
				// �Ժ���Ĵ���  
				String[] arr = PinyinHelper.toHanyuPinyinStringArray( ch , format );
				if( arr == null || arr.length == 0 )
				{
					continue;
				}
				String s = arr[0];// ���ܶ�����,ֻȡ��һ��  
				char c = s.charAt( 0 );// ��д��һ����ĸ  
				String pinyin = String.valueOf( c ).toUpperCase().concat( s.substring( 1 ) );
				sb.append( pinyin ).append( "" );
			}
			return sb.toString();
		}
		catch( BadHanyuPinyinOutputFormatCombination e )
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/** 
	 * ��ȡ�����һ���ֵ���Ӣ����ĸ 
	 * @param src 
	 * @return 
	 */
	public static String getTerm(
			String src )
	{
		String res = chinese2pinyin( src );
		if( res != null && res.length() > 0 )
		{
			return res.toUpperCase().charAt( 0 ) + "";
		}
		else
		{
			return "OT";
		}
	}
	
	/**
	 * Delete all the space in the string
	 * 
	 * @param str
	 * @return
	 */
	public static String deleteSpace(
			String str )
	{
		String result = "";
		for( String s : str.split( " " ) )
		{
			result += s;
		}
		return result;
	}
	
	/**
	 * Divided by space, get the first letter
	 * 
	 * @param str
	 * @return
	 */
	public static String getFirstLetter(
			String str )
	{
		String result = "";
		if( !str.isEmpty() )
		{
			for( String s : str.split( " " ) )
			{
				result += s.charAt( 0 );
			}
		}
		return result;
	}
}
