package ru.badretdinov.restapi.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public abstract class CommonController {
    protected static enum Code {
        OK(200), // всё в порядке (200)
        BAD_REQUEST(400), // ошибка на стороне клиента (400)
        INTERNAL_SERVER_ERROR(500); // ошибка на стороне сервера (500)

        private int code;

        private Code(final int code) {
            this.code = code;
        }

        public int getValue() {
            return this.code;
        }

        public static Code valueOf(final int code) {
            switch (code) {
                case 200:
                    return Code.OK;
                case 400:
                    return Code.BAD_REQUEST;
                case 500:
                    return Code.INTERNAL_SERVER_ERROR;
                default:
                    return null;
            }
        }
    }

    // Результат выполнения
    @JsonInclude(Include.NON_NULL)
    protected static class Result {
        public final int code; // код ответа
        public final Object data; // данные
        public final String info; // дополнительная информация

        public Result(final Code code, final Object data, final String info) {
            this.code = code.getValue();
            this.data = data;
            this.info = info;
        }

        public Result(final Code code, final Object data) {
            this(code, data, null);
        }

        public Result(final Code code, final String info) {
            this(code, null, info);
        }

        public Result(final Code code) {
            this(code, null, null);
        }
    }

    // успешный ответ, данные есть
    public final Result ok(final Object data) {
        return new Result(Code.OK, data);
    }

    // успешный ответ, данных нет
    public final Result ok() {
        return this.ok(null);
    }

    // Ответ с ошибкой, с дополнительной информацией
    public final Result error(final Code code, final String info) {
        return (code == Code.BAD_REQUEST || code == Code.INTERNAL_SERVER_ERROR ? new Result(code, info) : null);
    }

    // Ответ с ошибкой, без дополнительной информации
    public final Result error(final Code code) {
        return this.error(code, null);
    }

}
