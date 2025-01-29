import pyttsx3
import speech_recognition as sr
import datetime as dt
import wikipedia
import webbrowser
import pyjokes

def speak(text):
    engine.say(text)
    engine.runAndWait()

def wish():
    time = dt.datetime.now().hour
    if(time < 12 and time >=5 ):
        speak("Good Morning. Have a nice day. Welcome to D3vil")
    elif(time>=12 and time <16):
        speak("Good Afternoon. Welcome to D3vil")
    elif(time>=16 and time<=24):
        speak("Good Evening. Welcome to D3vil")
    elif(time>=1 and time<5):
        speak("Sorry to say but I am a bit confused whether i should say Good morning or good night")

def detectVoice():
    r=sr.Recognizer()
    with sr.Microphone() as source:
        print("Listening...")
        r.pause_threshold=0.5
        audio=r.listen(source)
        print("Recognizing..")
    try:
        query=r.recognize_google(audio,language="en-in")
        print("You said:",query)
        return query
    except sr.UnknownValueError:
        print ("I could not understand your audio")
        return "None"
    except sr.RequestError as e:
        print("I Could not request results service; {0}".format(e))
        return "None"

def username():
    choice='no'
    while 'no' in choice:
        def call():
            print("What should I call you")
            speak("What should I call you")
            name=detectVoice()
            if name!=None:
                print("Welcome mister:",name)
                speak("Welcome mister")
                speak(name)
            else:
                print("I didn't get your name correctly. Please repeat")
                speak("I didn't get your name correctly. Please repeat")
                name=call()
            return name
        name=call()
        print("Did i get your name correctly. Reply with yes or no")
        speak("Did i get your name correctly reply with yes or no")
        choice = detectVoice()
    hru="How are you mister "
    print(hru,name)
    hru =hru+name
    speak(hru)
    feel=detectVoice()
    print(feel)
    if feel!="None":
        if 'fine' or 'good' in feel:
            print("It's good to know that your fine")
            speak("It's good to know that your fine")
            if (feel.find("you")!=-1 or feel.find("about")!=-1):
                print("Thank your for asking. I am also doing great. Just experiencing this new world")
                speak("Thank your for asking. I am also doing great. Just experiencing this new world")
            else:
                print("You could've also asked that how I am feeling. No one cares about my feelings")
                speak("You could've also asked that how I am feeling. No one cares about my feelings. sad life ")

def assitant():
    choice="no"
    speak("What you want to find")
    query=detectVoice()
    if 'wikipedia' in query:
        query=query.replace("wikipedia","")
        result =wikipedia.summary(query,sentences='2')
        print(result)
        speak(result)
    elif 'youtube' in query:
        if 'search' or 'find' in query:
            query = query.replace("search", "")
            query = query.replace("find", "")
        query = query.replace("youtube", "")
        chrome_path = 'C:/Program Files (x86)/Google/Chrome/Application/chrome.exe %s'
        link="https://www.youtube.com/results?search_query="
        url=link+query
        webbrowser.get(chrome_path).open(url)
    elif 'who made you' in query or 'who created you' in query or 'created you' in query:
        print("Mr Abk created me. boring guy")
        speak("Mr Abk created me. boring guy")
    elif 'joke' in query:
        joke=pyjokes.get_joke()
        print(joke)
        speak(joke)
    elif "who are you" in query:
        speak("I am your virtual assistant made by Abk")
    speak("Do you want to find something else")
    choice=detectVoice()
    if 'yes' in choice:
        assitant()

engine=pyttsx3.init()
voices=engine.getProperty('voices')
engine.setProperty('voice',voices[1].id)
engine.setProperty('rate',150)

wish()
username()
assitant()

joke=pyjokes.get_joke()
print(joke)
speak(joke)