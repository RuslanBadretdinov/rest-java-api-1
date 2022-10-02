package ru.badretdinov.restapi.storages;

import ru.badretdinov.restapi.models.CommonModel;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;

public final class FileStorage<T extends CommonModel> extends CommonStorage<T> {
    // разделитель полей в строке
    private static final String FIELD_SEPARATOR = "\t";

    // имя файла для хранения данных
    private final String fileName;

    // конструктор
    public FileStorage(final String fileName , final Class<T> dataClass) {
        super(dataClass);
        this.fileName = fileName;
    }

    @Override
    public void readAll() {
        this.dataList.clear();

        File file = new File(this.fileName);
        if (file.exists() && file.isFile() && file.canRead()) {
            try {
                final RandomAccessFile raf = new RandomAccessFile(file, "r");
                String utf;

                while ((utf = raf.readUTF()) != null) {
                    try {
                        this.dataList.add(
                                dataClass.getDeclaredConstructor(String[].class).newInstance(
                                        new Object[] { utf.split(FIELD_SEPARATOR)}
                                )
                        );
                    } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException exc) {}
                }
                raf.close();
            } catch (IOException exc) {
            }
        }
    }

    @Override
    public void writeAll() {
        File file = new File(this.fileName);
        try {
            final RandomAccessFile raf = new RandomAccessFile(file, "rw");

            raf.setLength(0);
            this.dataList.forEach((T dataItem) -> {
                try {
                    raf.writeUTF(String.join(FIELD_SEPARATOR, dataItem.toFields()));
                } catch (IOException exc) {}
            });
            raf.close();
        } catch (IOException exc) {
        }
    }
}
