package org.example;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SendMessage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ValuteBot {
    public static void main(String[] args) {
        TelegramBot bot = new TelegramBot("");

        bot.setUpdatesListener(updates -> {
            updates.forEach(upd -> {
                try {
                    System.out.println(upd);
                    long chatId = upd.message().chat().id();
                    String incomeMessage = upd.message().text();
                    //logic
                    String date = incomeMessage; //date=2022-08-09
                    Document doc = Jsoup.connect("https://www.cbr.ru/scripts/XML_daily.asp?date_req=" + date).get();
                    System.out.println(doc.title());
                    Elements valutes = doc.select("Valute");
                    String result = "";
                    for (Element valute : valutes) {
                        if (valute.attr("ID").equals("R01235")) {
                            result = valute.select("Value").text();
                            System.out.println(result);
                        }
                    }
                    //send response
                    SendMessage request = new SendMessage(chatId, result);
                    bot.execute(request);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            });
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });

    }
}
