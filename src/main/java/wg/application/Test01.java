package wg.application;

import wg.application.util.JdbcUtil;

public class Test01 {

    // public static void main(String[] args) {
    //
    //     CloseableHttpClient httpclient = HttpClients.createDefault();
    //     HttpGet httpget = new HttpGet("url");
    //     List<NameValuePair> formparams = new ArrayList<NameValuePair>();
    //     UrlEncodedFormEntity uefEntity;
    //     try {
    //         uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
    //         CloseableHttpResponse response = httpclient.execute(httpget);
    //         String responseStr = null;
    //         try {
    //             HttpEntity entity = response.getEntity();
    //             if (entity != null) {
    //                 responseStr = EntityUtils.toString(entity, "UTF-8");
    //             }
    //         } finally {
    //             //response.close();
    //         }
    //         StatusLine statusLine = response.getStatusLine();
    //         switch (statusLine.getStatusCode()) {
    //             case 200:
    //             default:
    //                 break;
    //         }
    //     } catch (final IOException e) {
    //         warning(e.getMessage());
    //     } finally {
    //         httppost.releaseConnection();
    //     }
    // }
    
}
