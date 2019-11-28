<template>
    <Page>
        <ActionBar>
          <StackLayout orientation="horizontal">
          <Label text="BUDAPEST, HU" fontSize="24" verticalAlignment="center" />
          </StackLayout>
        </ActionBar>
         
        <GridLayout columns="*" rows="*">
            <ListView for="item in weather" class="list-group">
              <v-template>
                <GridLayout columns="85, auto, *" rows="auto, auto, *">
                <Image @tap="showToast" desc="This is picture" :src="item.iconLink" row="0" col="0" rowSpan="3" />
                <Label @tap="showToast" desc="This is Datetime" row="0" col="1"> <FormattedString><Span fontSize="17" fontWeight="bold" :text="item.utcTime | pretty"></Span></FormattedString></Label>
                <Label @tap="showToast" desc="This is temperature" row="1" col="1"><FormattedString><Span fontSize="17" style="color: red">{{ item.highTemperature }}°C / {{item.highTemperature | fahrenheit}}°F</Span></FormattedString></Label>
                <Label @tap="showToast" desc="This is description" row="2" col="1"><FormattedString><Span fontSize="17">{{ item.description }}</Span></FormattedString></Label>
                </GridLayout>
              </v-template>
            </ListView>
            
        </GridLayout>
    </Page>
</template>

<script >
import * as http from "http";
import * as moment from "moment";
import * as application from "application";

  export default {
    data() {
      return {
        weather: []
      }
    },
    mounted() {    
      this.getWeather("oQmZJV34d8qXolJooKkv", 
      "VAN-cy5jN4j4mURpDxZrOw",{
        latitude: 37.7397,
        longitude: -121.4252
      });
    },
    methods: {
      showToast(args) {
        const context = application.android.context;
        var toaster =new org.nativescript.robotcontrol.toaster.Toaster();
        toaster.showToast(context,args.object.desc,"long");
    },
      getWeather(appId, appCode, position) {
        const params = {
          product: "forecast_7days_simple",
          latitude: position.latitude,
          longitude: position.longitude,
          app_id: appId,
          app_code : appCode
        }
        http.getJSON("https://weather.api.here.com/weather/1.0/report.json" + this.encodeQueryParameters(params)).then(result =>{
            this.weather = result.dailyForecasts.forecastLocation.forecast;           
        }, error =>{
          console.error(error);
        })
      },
      encodeQueryParameters(params) {
        const encodeParams = [];
        for(const key in params) {
          if(params.hasOwnProperty(key))
          {
            encodeParams.push(key + "=" + encodeURIComponent(params[key]));
          }
        }
        return "?" + encodeParams.join("&");
      }
    },
    filters: {
      pretty(value) {
        return moment(value).format("MMMM DD, YYYY");
      },
      fahrenheit(value) {
        return ((value * (9/5)) +32).toFixed(2);
      }
    }
  }
</script>

<style scoped>
    ActionBar {
        background-color: #ffffff;
        color: #000000;
    }

    .message {
        vertical-align: center;
        text-align: center;
        font-size: 20;
        color: #333333;
    }
</style>
