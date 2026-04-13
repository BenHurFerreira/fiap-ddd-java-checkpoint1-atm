import java.util.Scanner;

public class FiapBankAtm {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // ── Fase A: Cadastro de Dados ────────────────────────────────────────

        System.out.print("Digite seu nome completo: ");
        String nomeCompleto = scanner.nextLine().trim();

        String primeiroNome = nomeCompleto.split(" ")[0];
        System.out.println("Bem-vindo(a), " + primeiroNome + "!");

        // Cadastro de senha forte
        String regexSenhaForte = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[!@#$%^&*()\\-_+=?><]).{8,}$";
        String senhaCadastrada = "";
        boolean senhaValida = false;

        System.out.println("\n--- Cadastro de Senha ---");
        System.out.println("A senha deve ter: minimo 8 caracteres, uma letra maiuscula,");
        System.out.println("um numero e um caractere especial (!@#$%^&*()-_+=?><).");

        while (!senhaValida) {
            System.out.print("Crie sua senha: ");
            String senhaDigitadaCadastro = scanner.nextLine();

            if (senhaDigitadaCadastro.matches(regexSenhaForte)) {
                senhaCadastrada = senhaDigitadaCadastro;
                senhaValida = true;
                System.out.println("Senha cadastrada com sucesso!");
            } else {
                System.out.println("Senha fraca. Tente novamente atendendo a todos os criterios.");
            }
        }

        // ── Autenticacao (Login) ─────────────────────────────────────────────

        System.out.println("\n--- Autenticacao ---");

        int tentativas = 0;
        boolean autenticado = false;

        while (tentativas < 3 && !autenticado) {
            System.out.print("Digite sua senha: ");
            String senhaDigitada = scanner.nextLine();

            if (senhaDigitada.equals(senhaCadastrada)) {
                autenticado = true;
            } else {
                tentativas++;
                int tentativasRestantes = 3 - tentativas;
                if (tentativasRestantes > 0) {
                    System.out.println("Senha incorreta. Tentativas restantes: " + tentativasRestantes);
                }
            }
        }

        if (!autenticado) {
            System.out.println("ACESSO BLOQUEADO");
            scanner.close();
            return;
        }

        System.out.println("Acesso autorizado. Bom atendimento, " + primeiroNome + "!");

        // ── Fase B: Menu Principal ───────────────────────────────────────────

        double saldo = 0.0;
        int opcao = 0;

        do {
            System.out.println("\n=============================");
            System.out.println("     FIAP Bank - Menu        ");
            System.out.println("=============================");
            System.out.println("[ 1 ] Consultar Saldo");
            System.out.println("[ 2 ] Fazer Deposito");
            System.out.println("[ 3 ] Fazer Saque");
            System.out.println("[ 4 ] Sair");
            System.out.println("=============================");
            System.out.print("Escolha uma opcao: ");

            String entradaOpcao = scanner.nextLine();

            try {
                opcao = Integer.parseInt(entradaOpcao);
            } catch (NumberFormatException e) {
                System.out.println("Opcao invalida. Digite um numero entre 1 e 4.");
                opcao = 0;
                continue;
            }

            // ── Fase C: Operacoes Bancarias ──────────────────────────────────

            switch (opcao) {

                case 1:
                    System.out.printf("Saldo atual: R$ %.2f%n", saldo);
                    break;

                case 2:
                    System.out.print("Informe o valor do deposito: R$ ");
                    String entradaDeposito = scanner.nextLine();
                    try {
                        double valorDeposito = Double.parseDouble(entradaDeposito.replace(",", "."));
                        if (valorDeposito <= 0) {
                            System.out.println("Valor invalido. O deposito deve ser maior que zero.");
                        } else {
                            saldo += valorDeposito;
                            System.out.printf("Deposito de R$ %.2f realizado com sucesso!%n", valorDeposito);
                            System.out.printf("Novo saldo: R$ %.2f%n", saldo);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Valor invalido. Digite um numero valido.");
                    }
                    break;

                case 3:
                    System.out.print("Informe o valor do saque: R$ ");
                    String entradaSaque = scanner.nextLine();
                    try {
                        double valorSaque = Double.parseDouble(entradaSaque.replace(",", "."));
                        if (valorSaque <= 0) {
                            System.out.println("Valor invalido. O saque deve ser maior que zero.");
                        } else if (valorSaque > saldo) {
                            System.out.println("Saldo insuficiente para realizar o saque.");
                            System.out.printf("Saldo disponivel: R$ %.2f%n", saldo);
                        } else {
                            saldo -= valorSaque;
                            System.out.printf("Saque de R$ %.2f realizado com sucesso!%n", valorSaque);
                            System.out.printf("Novo saldo: R$ %.2f%n", saldo);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Valor invalido. Digite um numero valido.");
                    }
                    break;

                case 4:
                    System.out.println("O FIAP Bank agradece sua preferencia!");
                    break;

                default:
                    System.out.println("Opcao invalida. Escolha entre 1 e 4.");
                    break;
            }

        } while (opcao != 4);

        scanner.close();
    }
}
