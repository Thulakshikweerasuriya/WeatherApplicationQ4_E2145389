This is a simple weather application that retrieves and displays weather information for a specified city. 
It uses the OpenWeatherMap API to fetch weather data and displays the current temperature, humidity, and weather description. 
Additionally, the application geocodes the city's coordinates to provide the country, province, and city information.


The main features are,

1.Fetch and display current weather data including temperature, humidity, and weather description.
2.Reverse geocode the city's coordinates to display the country, province, and city.
3.Save the last searched city and load it on the next launch.


Project Structure

1.MainActivity.java

DownloadJSON: An AsyncTask to fetch JSON data from the given URL.
Loading: A method to load weather data for the specified city. It fetches weather data, reverse geocodes the coordinates, and updates the UI with the fetched data.
onCreate: Sets up the initial UI, restores the last searched city, and sets up the touch listener to clear the EditText field.


2.activity_main.xml

This is the main layout file, defining the UI components.

RelativeLayout: The root layout containing other views.
EditText: Input field for the city name.
Button: Triggers the Loading method to fetch weather data.
TextView: Displays the fetched weather data.
RelativeLayout (rlWeather): A container for displaying weather information, initially invisible.


3.AndroidManifest.xml

Defines the necessary permissions and main activity.

Permissions: INTERNET permission to allow network access.
Application: Configures the app including network security settings.
Activity: MainActivity is set as the launcher activity.



Setup instructions

Prerequisites are,
1.Android Studio
2.API key from OpenWeatherMap (2315437a225a1268ac2135ee4f11fa2d)



Steps

1.Clone the repository

2.Open the project in android studio

Launch android studio
Select 'file>open> and navigate to the cloned repository directory.
Open the project

3.Add API key

Open MainActivity.java file
Replace the API key with the user own API key from OpenWeatherMap.

String key = "2315437a225a1268ac2135ee4f11fa2d";

4.Run the Application

Connect user Android device or start an emulator.
Click on the Run button in Android Studio.



How to use,
1.Enter the name of the city in the EditText field.
2.Click the "Enter" button.
3.The application will fetch and display the weather data and geocoded address for the specified city.
4.And save the last searched city nam and load it when the app starts.

