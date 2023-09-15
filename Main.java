import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            // Создаем процесс для выполнения команды
            Process process = Runtime.getRuntime().exec("tasklist /FO CSV");

            // Получаем вывод команды
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            List<Integer> pidList = new ArrayList<>();
            List<String> processNames = new ArrayList<>();

            // Читаем вывод и извлекаем PID и имя процесса
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith("\"Image Name")) {
                    String[] parts = line.split(",");
                    if (parts.length >= 2) {
                        String pidString = parts[1].replace("\"", "").trim();
                        String processName = parts[0].replace("\"", "").trim();
                        try {
                            int pid = Integer.parseInt(pidString);
                            pidList.add(pid);
                            processNames.add(processName);
                        } catch (NumberFormatException e) {
                            // Пропускаем некорректные значения PID
                        }
                    }
                }
            }

            // Закрываем поток чтения
            reader.close();

            // Сортируем список PID и соответствующие имена процессов
            Collections.sort(pidList);
            Collections.sort(processNames);

            // Выводим отсортированный список PID и имен на экран
            System.out.println("Отсортированный список PID и имен процессов:");
            for (int i = 0; i < pidList.size(); i++) {
                System.out.println("PID: " + pidList.get(i) + ", Имя: " + processNames.get(i));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
