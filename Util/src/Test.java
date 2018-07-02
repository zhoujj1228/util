import java.io.File;
import java.util.List;

import util.FileUtil;
import util.HttpUtil;
import util.PatternUtil;
import util.PropertyUtil;
import util.StringUtil;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//File file = new File("C:\\eclipseWorkspace\\UnitTest\\src\\reportConfig.properties");
		//PropertyUtil.initPropertyWithEncoding(file, "UTF-8");
		//PropertyUtil.initPropertyNoEncoding(file);
		
		/*String a = "123aaa67bbb88vvvv9944";
		List<String> s = PatternUtil.getStringByPattern("[0-9]{2}", a);
		for(String ss : s){
			System.out.println(ss);
		}
		*/
		//String responseString = "<SDOROOT package_type=\"xml\"> 	<SYS_HEAD> 		<TRAN_TIMESTAMP>162625284</TRAN_TIMESTAMP> 		<TRAN_DATE>20160302</TRAN_DATE> 		<SERVICE_SCENE>01</SERVICE_SCENE> 		<CONSUMER_ID>030006</CONSUMER_ID> 		<SERVICE_CODE>11002000066</SERVICE_CODE> 		<ORG_SYS_ID>030006    </ORG_SYS_ID> 		<ESB_SEQ_NO>C1-20160308162625-001167</ESB_SEQ_NO> 		<CONSUMER_SEQ_NO>0300062016030200649129</CONSUMER_SEQ_NO> 	</SYS_HEAD> 	<APP_HEAD> 		<USER_ID>E9990   </USER_ID> 		<REVERSAL_DATE></REVERSAL_DATE> 		<AUTH_USER_ID_ARRAY> 			<SDO> 				<AUTH_PASSWORD>                </AUTH_PASSWORD> 				<AUTH_USER_ID>        </AUTH_USER_ID> 			</SDO> 			<SDO> 				<AUTH_PASSWORD>                </AUTH_PASSWORD> 				<AUTH_USER_ID>        </AUTH_USER_ID> 			</SDO> 		</AUTH_USER_ID_ARRAY> 		<REVERSAL_SEQ_NO>00000000</REVERSAL_SEQ_NO> 		<BRANCH_ID>9990      </BRANCH_ID> 		<BIZ_SEQ_NO>00007370</BIZ_SEQ_NO> 	</APP_HEAD> 	<LOCAL_HEAD> 		<RURAL_BRANCH_ID>0000</RURAL_BRANCH_ID> 		<CHANNEL_CODE>000001</CHANNEL_CODE> 		<BUS_SEQ_NO>0300062016030200649129</BUS_SEQ_NO> 	</LOCAL_HEAD> 	<BODY> 		<ACCT_BAL>431.50</ACCT_BAL> 		<TRANS_AMT>6.00</TRANS_AMT> 		<TRANS_TIME>131948</TRANS_TIME> 		<TRANS_DATE>20160307</TRANS_DATE> 		<ACCT_TYPE>1</ACCT_TYPE> 		<DR_CR_FLAG>2</DR_CR_FLAG> 		<ACCT_NO>6231288400001952761</ACCT_NO> 		<REMARK>bbb</REMARK> 	</BODY> </SDOROOT>";
		//Httptil.createHttpServer(10, responseString, "/test", 8766);
		/*String s = StringUtil.getStringByFlag(responseString, "package_type", 2, 3);
		System.out.println(s);*/
		String s = "sldjfajsdlfjldsjflhttp://123.8.9.11:9098/web/ace1230987654.mp3/56789076\n45shhttp://123.mp3dkfjhksjd";
		List<String> list = PatternUtil.getStringByPattern("http://.*\\.mp3", s);
		System.out.println(list.size());
		for(String result : list){
			System.out.println(result);
		}
	}

}
