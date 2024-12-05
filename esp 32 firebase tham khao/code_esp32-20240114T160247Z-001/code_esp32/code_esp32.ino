#include <DHT.h>                 
#define DHTTYPE DHT11
#define DHTPIN  13
DHT dht(DHTPIN, DHTTYPE);
#define quat 25
#define den  26
#define thietbi1  18
#define thietbi2  19
#include <WiFi.h>
#include <FirebaseESP32.h>
#include <ArduinoJson.h>
#define FIREBASE_HOST  "project-cuoiky-b282e-default-rtdb.asia-southeast1.firebasedatabase.app" 
#define FIREBASE_AUTH "HVJI5vWT474M9XItoIOdSEF39Gw8zFIR0VvVsqqg"   
#define WIFI_SSID "project_cuoiky"   
#define WIFI_PASSWORD "muoidiem"
FirebaseData firebase_data; 
float Tm,Tc ;
String relay1, relay2; 
int dosangden, tocdoquat, hm, hc;

void ketnoiwifi()
{
  Serial.begin(9600);
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("connecting");
  while (WiFi.status() != WL_CONNECTED) 
  {
    Serial.print(".");
    delay(300);
  }
  Serial.println();
  Serial.print("connected: ");
  Serial.println(WiFi.localIP());
}

void tach_dulieu(String dulieu)// dữ liệu đọc về có dạng 255#255#1#0
{
  ///chuyển String thành Char
  int dodai_dulieu_mang = dulieu.length()+1;
  char dulieu_mang[dodai_dulieu_mang];
  dulieu.toCharArray(dulieu_mang, dodai_dulieu_mang);

  String dosangden_str, tocdoquat_str, r1_str, r2_str;

  int tt=0;
  char *p = strtok(dulieu_mang, "#");
  dosangden_str=p;

  int dodai_mangD = dosangden_str.length()+1;
  char Den[dodai_mangD];
  dosangden_str.toCharArray(Den, dodai_mangD);
  dosangden = atoi(Den);////dung char

  while ( p != NULL )
  {
    p = strtok( NULL, "#");
    if (tt==0)
    {
      tocdoquat_str = p;
      int dodai_mangQ = tocdoquat_str.length()+1;
      char QUAT[dodai_mangQ];
      tocdoquat_str.toCharArray(QUAT, dodai_mangQ);
      tocdoquat = atoi(QUAT);////dung char
      tt=1;
    }
    else if (tt==1)
    {
      relay1 = p;
      tt=2;
    }
    else if (tt==2)
    {
      relay2 = p;
      break;
    }
  }
}

void setup() 
{
  ketnoiwifi();
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
  pinMode(DHTPIN, OUTPUT);
  pinMode(den, OUTPUT);
  pinMode(quat, OUTPUT);
  pinMode(thietbi1, OUTPUT);
  pinMode(thietbi2, OUTPUT);
  dht.begin();
  ledcAttachPin(den, 0);
  ledcSetup(0, 1000,8);
  ledcAttachPin(quat, 1);
  ledcSetup(1, 1000,8);
}

void loop() 
{
    if (WiFi.status() != WL_CONNECTED) ketnoiwifi();// kiểm tra kết nối Wifi

    hm = dht.readHumidity();// đo độ ẩm
    Tm = dht.readTemperature();//đo nhiệt độ

    if ( Tm != Tc| hm!= hc ) // kiểm tra có sự thay đổi nhiệt độ, độ ẩm
    {
       Tc=Tm;
       hc=hm;
       Firebase.setFloat(firebase_data,"/esp_guilen/nhietdo",Tc );//gửi giá trị nhiệt độ lên firebase
       Firebase.setInt(firebase_data,"/esp_guilen/doam",hc );////gửi giá trị độ ẩm lên firebase
    }
    
    Firebase.getString(firebase_data,"android_guilen/chuoidulieu");// đọc chuỗi dữ liệu từ firebase
    tach_dulieu(firebase_data.stringData());

    // điều khiển đèn
    ledcWrite(0,dosangden);
    // điều khiển quạt
    ledcWrite(1,tocdoquat);
    // điều khiển relay1
    if    (relay1=="1") digitalWrite(thietbi1, HIGH);
    else                digitalWrite(thietbi1, LOW);
    // điều khiển relay2
    if    (relay2=="1") digitalWrite(thietbi2, HIGH);
    else                digitalWrite(thietbi2, LOW);
} 



