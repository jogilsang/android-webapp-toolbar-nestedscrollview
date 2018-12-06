package talk.com.hongje.webapp;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    WebView mWebView;
    TextView errorVeiw;

    // 상단 바
//    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 웹뷰와 에러뷰
        errorVeiw = (TextView) findViewById(R.id.net_error_view);
        mWebView = (WebView) findViewById(R.id.activity_main_webview);

        // 상단 툴바 설정
        //setToolbar();

        /// 웹 세팅
        WebSettings webSettings = mWebView.getSettings();

        // 자바스크립트 허용
        webSettings.setJavaScriptEnabled(true);

        // 웹뷰 클라이언트 설정
        mWebView.setWebViewClient(new WebViewClient() {

            @Override

            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                // 사이트 url을 본다
                view.loadUrl(url);

                return true;

            }

            //네트워크연결에러
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

                switch (errorCode) {

                    // 서버에서 사용자 인증 실패
                    case ERROR_AUTHENTICATION:
                        break;

                    case ERROR_BAD_URL:
                        break;                           // 잘못된 URL

                    case ERROR_CONNECT:
                        break;                          // 서버로 연결 실패

                    case ERROR_FAILED_SSL_HANDSHAKE:
                        break;    // SSL handshake 수행 실패

                    case ERROR_FILE:
                        break;                                  // 일반 파일 오류

                    case ERROR_FILE_NOT_FOUND:
                        break;               // 파일을 찾을 수 없습니다

                    case ERROR_HOST_LOOKUP:
                        break;           // 서버 또는 프록시 호스트 이름 조회 실패

                    case ERROR_IO:
                        break;                              // 서버에서 읽거나 서버로 쓰기 실패

                    case ERROR_PROXY_AUTHENTICATION:
                        break;   // 프록시에서 사용자 인증 실패

                    case ERROR_REDIRECT_LOOP:
                        break;               // 너무 많은 리디렉션

                    case ERROR_TIMEOUT:
                        break;                          // 연결 시간 초과

                    case ERROR_TOO_MANY_REQUESTS:
                        break;     // 페이지 로드중 너무 많은 요청 발생

                    case ERROR_UNKNOWN:
                        break;                        // 일반 오류

                    case ERROR_UNSUPPORTED_AUTH_SCHEME:
                        break; // 지원되지 않는 인증 체계

                    case ERROR_UNSUPPORTED_SCHEME:
                        break;          // URI가 지원되지 않는 방식

                }

                super.onReceivedError(view, errorCode, description, failingUrl);

                mWebView.setVisibility(View.GONE);
                errorVeiw.setVisibility(View.VISIBLE);

            }

        });

        mWebView.setWebChromeClient(new WebChromeClient() {

            //alert 처리

            @Override

            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {

                new AlertDialog.Builder(view.getContext())

                        .setTitle("알림")

                        .setMessage(message)

                        .setPositiveButton(android.R.string.ok,

                                new AlertDialog.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {

                                        result.confirm();

                                    }

                                })

                        .setCancelable(false)

                        .create()

                        .show();

                return true;

            }


            //confirm 처리
            @Override
            public boolean onJsConfirm(WebView view, String url, String message,

                                       final JsResult result) {

                new AlertDialog.Builder(view.getContext())

                        .setTitle("알림")

                        .setMessage(message)

                        .setPositiveButton("Yes",

                                new AlertDialog.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {

                                        result.confirm();

                                    }

                                })

                        .setNegativeButton("No",

                                new AlertDialog.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {

                                        result.cancel();

                                    }

                                })

                        .setCancelable(false)

                        .create()

                        .show();

                return true;

            }

        });

        // url 설정
        mWebView.loadUrl(getString(R.string.url));


    }



    // 백버튼 이벤트 발생시 처리
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 웹에서 뒤로가기를 해야한다면
            if (mWebView.canGoBack()) {
                mWebView.goBack();
                return false;
            }
            // 뒤로가기 할게없으면 앱 종료
            else {
                // 앱 종료
                actionExit();
            }
        }

        return super.onKeyDown(keyCode, event);

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu, this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return true;
//    }
//
//    public void setToolbar(){
//
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//
//        setSupportActionBar(toolbar);
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setTitle("");
//        }
//
//        toolbar.setSubtitle("");
//        toolbar.inflateMenu(R.menu.menu);
//    }

    public void actionExit() {

        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        //dlg.setTitle("버튼 1개 대화상자"); // 제목
        dlg.setMessage(getString(R.string.action_exit_question)); // 내용
        //dlg.setIcon(R.drawable.ic_launcher); // 아이콘

        dlg.setNegativeButton(getString(R.string.message_yes), new  DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                // 종료하겠습니다
                moveTaskToBack(true);
                finish();

            }
        });

        dlg.setPositiveButton(getString(R.string.message_no), new  DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // onClick 메소드 매개변수가 DialogInterface 여야하네.
                //Toast.makeText(this, "종료",0).show();

            }
        });

        dlg.show(); // 보이다

    }





}