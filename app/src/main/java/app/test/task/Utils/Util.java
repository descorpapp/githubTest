package app.test.task.Utils;

import android.widget.Toast;

import app.test.task.App;
import app.test.task.R;

public class Util {
    public static void showErrorMsg(String msg) {
        Toast.makeText(App.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void handleForbiddenState(int code) {
        if (code == 403) {
            Util.showErrorMsg(App.getContext().getResources().getString(R.string.api_rate_limit));
        }
    }
}
