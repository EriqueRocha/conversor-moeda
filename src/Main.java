import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Main {

    private static final String apiKey = "key";

    public static void main(String[] args) throws IOException {

        System.out.println("-----------------------");
        System.out.println("| Conversor de moedas |");
        System.out.println("-----------------------");
        System.out.println("");
        System.out.println("------ EXEMPLOS DE CÓDIGOS DE MOEDAS -------");
        System.out.println("|        BRL--> REAL, USD--> DOLLAR        |");
        System.out.println("--------------------------------------------");
        System.out.println("");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Digite '0'(zero) para encerrar o programa");

            System.out.println("Digite o código da moeda de referência");

            String moedaReferencia = scanner.nextLine().toUpperCase();

            if (moedaReferencia.equals("0")) {
                break;
            }

            System.out.println("Moeda de referência: " + moedaReferencia);

            System.out.println("Digite a quantidade da moeda de referência");

            String valor = "";
            boolean entradaValida = false;

            while (!entradaValida) {
                valor = scanner.nextLine();

                try {
                    float numero = Float.parseFloat(valor);
                    entradaValida = true;

                    System.out.println("Você digitou: " + numero);
                } catch (NumberFormatException e) {

                    System.out.println("Entrada inválida. Por favor, digite um valor numérico válido.");
                }
            }

            System.out.println("Valor: "+ moedaReferencia+" - "+ valor);

            System.out.println("Digite o código da moeda que deseja realizar a conversão");

            String moedaConversao = scanner.nextLine().toUpperCase();

            System.out.println("Converter para: " + moedaConversao);

            String url_str = "https://v6.exchangerate-api.com/v6/"+apiKey+"/pair/"+moedaReferencia+"/"+moedaConversao+"/"+valor.toString();

            URL url = new URL(url_str);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();
            int responseCode = request.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {

                JsonParser jp = new JsonParser();
                JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
                JsonObject jsonobj = root.getAsJsonObject();

                String moedaFinal = jsonobj.get("conversion_result").getAsString();
                System.out.println("------------------------------------");
                System.out.println("moeda de referência: "+moedaReferencia+" - " + valor);
                System.out.println("moeda de conversão: "+moedaConversao+" - " + moedaFinal);
                System.out.println("------------------------------------");

            }else {
                System.out.println("###############################");
                System.out.println("| Código de moeda inexistente |");
                System.out.println("###############################");
                System.out.println("REVISE O CÓDIGO DA MOEDA FORNECIDA E TENTE NOVAMENTE");
                System.out.println("-------------------------------------------------------");
            }
        }
    }
}