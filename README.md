🌱 EcoScan
Descubra o impacto ambiental dos produtos que você consome
Um aplicativo Android nativo que utiliza a leitura de códigos de barras para fornecer informações claras e visuais sobre o impacto ambiental e nutricional dos produtos alimentícios.

📱 Sobre o Projeto
EcoScan é um aplicativo educativo alinhado ao pilar Ambiental (E) do ESG que conscientiza consumidores sobre suas escolhas de compra. Ao escanear o código de barras de um produto, o app consulta a base de dados aberta Open Food Facts e exibe:
✅ EcoScore (A a E) - Impacto ambiental geral
✅ Grupo NOVA (1 a 4) - Grau de industrialização
✅ Análise de Ingredientes - Vegano, vegetariano, sem óleo de palma
✅ Níveis de Nutrientes - Gordura, sal, açúcar
✅ Dados Nutricionais - Energia (kcal), proteínas, fibras
✅ Rastreabilidade - Alertas de contaminação cruzada

🎯 Aplicação ESG
O projeto se alinha ao pilar Ambiental (Environmental) do ESG, contribuindo para:
IndicadorRelevânciaEcoScoreMede impacto ambiental considerando origem, embalagem e transporteNOVAQuanto menor, menos industrializado e menor pegada de carbonoÓleo de PalmaAssociado a desmatamento e perda de biodiversidadeDietas VegetaisPegada de carbono significativamente menorNutrientesProdutos menos processados têm menor impacto alimentarRastreabilidadeTransparência na cadeia produtiva (Governança ESG)

🛠️ Stack Tecnológico
ComponenteTecnologiaVersãoLinguagemKotlin2.0.21UI FrameworkJetpack Compose + Views (híbrido)-HTTP ClientRetrofit + OkHttp2.9 / 4.12Serialização JSONGson-Scanner de CódigoZXing Android Embedded4.3API de DadosOpen Food Facts REST APIPúblicaAmbienteAndroid Studio-compileSdkAndroid 36-targetSdkAndroid 36-minSdkAndroid 26 (Android 8.0)-

📋 Funcionalidades
Tela 1 - Home
Primeira tela do aplicativo com identidade visual e botão principal para iniciar a leitura.

Apresentação de marca
Botão "Escanear Produto" com navegação para câmera
Layout responsivo em Jetpack Compose

Tela 2 - Scanner de Código (Câmera)
Leitura automática de códigos EAN-13 e UPC-A com overlay visual.

Abertura da câmera nativa
Leitura automática com beep sonoro
Busca automática na API ao detectar código
Fallback para entrada manual se falhar

Tela 3 - Entrada Manual
Dialog para digitação manual do código de barras.

Campo numérico para EAN
Validação de campo vazio
Botões "Buscar" e "Cancelar"
Teclado numérico automático

Tela 4 - Carregamento
Estado intermediário durante requisição HTTP.

Toast com mensagem "Buscando produto..."
Requisição assíncrona via Retrofit (não trava UI)
Timeout configurado: 30s
Retry automático em caso de falha

Tela 5 - Resultado da Análise
Tela principal com todos os dados do produto em cards temáticos.

Card EcoScore (A-E com cores)
Card NOVA (1-4 com descrição)
Card Ingredientes (Badges: Vegano, Vegetariano, Sem Óleo de Palma)
Card Nutrientes (Gordura, Sal, Açúcar)
Card Nutricional (Kcal, Proteínas, Fibras)
Card Rastreabilidade (Alertas de contaminação)
Scroll vertical
Botão "Voltar ao Início"

Tela 6 - Erro
Estado exibido quando produto não é encontrado ou há falha de rede.

Toast com mensagem de erro
Reabertura automática do dialog de entrada manual
Permite nova tentativa


🚀 Como Instalar e Usar
Pré-requisitos

Android Studio (versão recente)
JDK 11 ou superior
Kotlin 2.0.21
Gradle 8.x

Instalação

Clone o repositório

bashgit clone https://github.com/seu-usuario/EcoScan.git
cd EcoScan

Abra no Android Studio

bashArquivo > Abrir > Selecione a pasta do projeto

Sincronize o Gradle

bashBuild > Make Project

Executa o app


Conecte um dispositivo Android ou use um emulador
Pressione Shift + F10 (ou clique no ícone Play)

Permissões Necessárias
O aplicativo requer:

CAMERA - Para ler códigos de barras
INTERNET - Para consultar a API Open Food Facts


📡 API Consumida
Open Food Facts REST API
Endpoint:
GET https://world.openfoodfacts.org/api/v0/product/{barcode}.json
Parâmetros:

barcode - Código EAN-13 ou UPC-A

Exemplo de Resposta:
json{
  "status": 1,
  "product": {
    "product_name": "Feijão Kicaldo",
    "brands": "Kicaldo",
    "ecoscore_grade": "unknown",
    "nova_group": 1,
    "ingredients_analysis_tags": ["en:vegan", "en:vegetarian", "en:palm-oil-free"],
    "nutrient_levels": {
      "fat": "low",
      "salt": "low",
      "sugars": "low"
    },
    "nutriments": {
      "energy-kcal_100g": 71,
      "proteins_100g": 4.8,
      "fiber_100g": 7.0
    },
    "traces": "pt:Pode conter soja"
  }
}
Documentação: https://wiki.openfoodfacts.org/API

🏗️ Arquitetura
EcoScan/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/fiap/ecoscan/
│   │   │   │       ├── ui/
│   │   │   │       │   ├── screens/
│   │   │   │       │   └── components/
│   │   │   │       ├── data/
│   │   │   │       │   ├── api/
│   │   │   │       │   └── models/
│   │   │   │       ├── viewmodel/
│   │   │   │       └── MainActivity.kt
│   │   │   └── res/
│   │   │       ├── drawable/
│   │   │       ├── values/
│   │   │       └── layout/
│   │   └── test/
│   └── build.gradle
└── README.md

📚 Dependências Principais
gradledependencies {
    // Jetpack Compose
    implementation "androidx.compose.ui:ui:1.x.x"
    implementation "androidx.compose.material3:material3:1.x.x"
    
    // Retrofit + OkHttp
    implementation "com.squareup.retrofit2:retrofit:2.9.0"
    implementation "com.squareup.retrofit2:converter-gson:2.9.0"
    implementation "com.squareup.okhttp3:okhttp:4.12.0"
    
    // Gson
    implementation "com.google.code.gson:gson:2.10.1"
    
    // ZXing (Barcode Scanner)
    implementation "com.journeyapps:zxing-android-embedded:4.3.0"
    
    // Lifecycle
    implementation "androidx.lifecycle:lifecycle-viewmodel-compose:2.6.x"
    implementation "androidx.lifecycle:lifecycle-runtime-compose:2.6.x"
}

📝 Exemplo de Uso

Abra o aplicativo
Clique em "Escanear Produto"
Aponte a câmera para o código de barras do produto
O app lerá automaticamente e consultará os dados
Visualize a análise ambiental e nutricional
Clique em "Voltar ao Início" para escanear outro produto


🔧 Configurações
Timeout de Requisição
Configurado em OkHttp com:

Connect Timeout: 30s
Read Timeout: 30s
Write Timeout: 30s

Retry Automático

1 tentativa de retry em caso de falha de rede
2 tentativas máximas totais antes de exibir erro

User-Agent
Header enviado para compatibilidade com a API Open Food Facts

🐛 Troubleshooting
Câmera não abre

Verifique permissões em Configurações > Privacidade
Reinicie o app
Tente em um dispositivo/emulador diferente

Produto não encontrado

Verifique se o código de barras está legível
Tente escanear outro produto
Use a entrada manual para verificar o código

Erro de rede

Verifique conexão com internet
O app fará retry automático
Tente novamente após alguns segundos


📊 Exemplo de Produto Testado
Feijão Kicaldo (EAN: 7896116900029)

EcoScore: Unknown
NOVA Group: 1 (Não processado)
Vegano ✅
Vegetariano ✅
Sem óleo de palma ✅
Energia: 71 kcal/100g
Proteínas: 4.8g
Fibras: 7.0g


📄 Licença
Este projeto é fornecido para fins educacionais - FIAP 2026.

👥 Contribuições
Este é um projeto acadêmico. Contribuições são bem-vindas!

Faça um Fork
Crie uma branch para sua feature (git checkout -b feature/MinhaFeature)
Commit suas mudanças (git commit -m 'Add MinhaFeature')
Push para a branch (git push origin feature/MinhaFeature)
Abra um Pull Request


📞 Suporte
Para dúvidas ou problemas:

Abra uma Issue no repositório
Consulte a documentação da Open Food Facts
Verifique a documentação do Android


🌍 ESG - Contribuição para Sustentabilidade
EcoScan contribui para objetivos de desenvolvimento sustentável:

🎯 ODS 12 - Consumo e Produção Responsáveis
🌍 ODS 13 - Ação Climática
❤️ ODS 3 - Saúde e Bem-estar


Desenvolvido com ❤️ por Lucas Rodrigues | FIAP 2026
