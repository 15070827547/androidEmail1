package mi.email.mailutil;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import mi.learn.com.R;

public class MainActivity extends Activity {
  private Button send;
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    send = (Button) findViewById(R.id.send);


    send.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        sendMessage("你好我的朋友111");
      }
    });
  }

  private void sendMessage(final String msg) {
    /*****************************************************/
    Log.i("shuxinshuxin", "开始发送邮件");
    //Toast.makeText(MainActivity.this,"发送成功", Toast.LENGTH_SHORT).show();
    // 这个类主要是设置邮件
    new Thread(new Runnable() {

      @Override
      public void run() {

        // TODO Auto-generated method stub
        MailSenderInfo mailInfo = new MailSenderInfo();
        mailInfo.setMailServerHost("smtp.126.com");
        mailInfo.setMailServerPort("25");
        mailInfo.setValidate(true);
        mailInfo.setUserName("a3068793203@126.com");
        mailInfo.setPassword("aa120112");// 您的邮箱密码
        mailInfo.setFromAddress("a3068793203@126.com");
        mailInfo.setToAddress("aa3068793203@126.com");
        mailInfo.setSubject("这是标题");
        mailInfo.setContent(msg);
        // 这个类主要来发送邮件
        SimpleMailSender sms = new SimpleMailSender();
        boolean isSuccess = sms.sendTextMail(mailInfo);// 发送文体格式
        // sms.sendHtmlMail(mailInfo);//发送html格式
        if (isSuccess) {
          Log.i("shuxinshuxin", "发送成功");
          //Toast.makeText(MainActivity.this,"发送成功",Toast.LENGTH_LONG).show();
        } else {
          Log.i("shuxinshuxin", "发送失败");
          //Toast.makeText(MainActivity.this,"发送失败",Toast.LENGTH_LONG).show();
        }
      }
    }).start();}
}
