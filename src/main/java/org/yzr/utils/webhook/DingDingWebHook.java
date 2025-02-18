package org.yzr.utils.webhook;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.yzr.model.App;
import org.yzr.model.WebHook;
import org.yzr.utils.ImageUtils;
import org.yzr.utils.PathManager;

import java.util.HashMap;
import java.util.Map;

public class DingDingWebHook implements IWebHook {

    private static OkHttpClient client = new OkHttpClient();
    /**
     * 发送钉钉消息
     * @param jsonString 消息内容
     * @param webhook 钉钉自定义机器人webhook
     * @return
     */
    private static boolean sendToDingding(String jsonString, String webhook) {
        try{
            String type = "application/json; charset=utf-8";
            RequestBody body = RequestBody.create(MediaType.parse(type), jsonString);
            Request.Builder builder = new Request.Builder().url(webhook);
            builder.addHeader("Content-Type", type).post(body);

            Request request = builder.build();
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            System.out.println(String.format("send ding message:%s", string));
            JSONObject result = JSONObject.parseObject(jsonString);
            System.out.println(result.getInteger("errcode"));
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void sendMessage(App app, PathManager pathManager) {
        if (app.getWebHookList() == null || app.getWebHookList().size() < 1) {
            return;
        }
        Map<String, Object> markdown = new HashMap<>();
        markdown.put("title", app.getName());
        String url = pathManager.getBaseURL(false) + "s/" + app.getShortCode();
        String platform = "iOS";
        if (app.getPlatform().equalsIgnoreCase("android")) {
            platform = "Android";
        }

        String appInfo = String.format("[%s(%s)更新](%s)", app.getName(), platform, url);
        // 将图片转为 base64, 内网 ip 钉钉无法访问，直接给图片数据
        String iconPath = pathManager.getFullPath(app.getCurrentPackage())  + "icon.jpg";
        String icon = "data:image/jpg;base64," + ImageUtils.convertImageToBase64(iconPath);
        String pathInfo = String.format("![%s](%s)", app.getName(), icon);
        String otherInfo = String.format("链接：[%s](%s) \n\n 版本：%s (Build: %s)", url, url, app.getCurrentPackage().getVersion(), app.getCurrentPackage().getBuildVersion());
        String text = appInfo + " \n\n " + pathInfo + " \n\n " + otherInfo;
        markdown.put("text", text);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msgtype", "markdown");
        jsonObject.put("markdown", markdown);
        String json = jsonObject.toJSONString();
        for (WebHook webHook :
                app.getWebHookList()) {
            sendToDingding(json, webHook.getUrl());
        }
    }
}
