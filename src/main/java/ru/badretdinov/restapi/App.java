package ru.badretdinov.restapi;

import static spark.Spark.*;
import spark.Spark;

import ru.badretdinov.restapi.controllers.ScheduleController;
import ru.badretdinov.restapi.models.ScheduleModel;
import ru.badretdinov.restapi.storages.FileStorage;

import static ru.badretdinov.restapi.transformers.JsonTransformers.JSON;


public class App {
    public static final String JSOM_MIME_TYPE = "application/json";

    private static final String SERVER_HOST = "127.0.0.1";
    private static int          SERVER_PORT = 8808;

    private static final String DEFAULT_FILE = "schedule.tsv";

    public static void main(String[] args) {
        // создание файлового хранилища
        final FileStorage<ScheduleModel> storage = new FileStorage<ScheduleModel>(
                args != null && args.length == 1 ? args[0] : DEFAULT_FILE,
                ScheduleModel.class
        );

        //чтение файла данных
        storage.readAll();

        // создание контроллера
        final ScheduleController controller = new ScheduleController(storage);

        // Конфигурация сервера
        ipAddress(SERVER_HOST);
        port(SERVER_PORT);

/**
 *         // Объявление роутов
 *         path("/api", ()->{
 *             get("/test", controller.test, JSON);
 *         });
 *         after((req, res)->{
 *             res.type(JSOM_MIME_TYPE);
 *         });
  */
        // Объявление роутов
        path("/api", ()->{
            get(    "/schedule",            controller.list,            JSON);
            get(    "/schedule/:timestamp", controller.find,            JSON);
            post(   "/schedule",            controller.createOrModify,  JSON);
            put(    "/schedule",            controller.createOrModify,  JSON);
            delete( "/schedule/:timestamp",  controller.kill,            JSON);
        });
        after((req, res)->{
            res.type(JSOM_MIME_TYPE);
        });

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // останов сервера
                Spark.stop();
                // сохранение данных в файл
                storage.writeAll();
            }
        });


    }
}
