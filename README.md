
Kleines Verzeichnis:

(1). Road To Alpha-Release 

(2). Ergänzungen für spätere Updates/Releases

(3). Anregungen, Ideen

(4). Kritikpunkte, Meinungen




****************************************************************************************************
**				**(1) ROAD TO ALPHA-RELEASE**					  **
****************************************************************************************************

**<<< DRAW >>>**
müssen uns auf eine Art einigen, wie mir die GameObjects auf die Leinwand knallen (array, bitmap)
#### Kevin: Tendiere stark dazu die Instanzen mit normalen Drawable-Int-Arrays zu versehen, aber zusätzlich
#### ein statisches Bitmap-Array (vorgeladen in initialize()) vom Drawable-Int-Array zugreifbar zu machen. Wenn 
#### verstehst was i ein.

**<<< ZUGRIFF AUF BITMAP-REFERENZEN >>>**
zugriffsmethoden für die einzelnen bilder (mir haben aktuell einerseits statische bild-referenzen, 
dann wieder die vererbten von da Superclass
#### siehe oben

**<<< GameDesign >>>**
EINZIGARTIGES GameDesign, evtl an menschlichen Spieler mit Jetpack? Gegner müssen sich im Kern 
ähneln, aktuell is des ja eher a Krautsalat haha
#### Stimmt, des auf jeden Fall! I würd unser Spiel aber eher darauf ausrichten, dass die Bitmaps
#### später vom User ausgetauscht werden können (zumindest es Player-Drawable), sonst is der App
#### Name bissl irreführend (aber des könn ma ja dann später mit Updates einführen).

**<<< IN-GAME CURRENCY >>>**
I denk des Coin-System is koa schlechte Idee, des müssma nur weiterdenken und ausreifen!
#### Auf alle Fälle und bedeuted money! Aber feilen ma besser derweil ma am bereits bestehenden
#### und im Highscore. Dann gehts zum Alpha und der Rest kommt von selbst. Im Alpha können wir 
#### ja dann schon bezahlte Werbung einblenden. 

**<<< LEVEL-SYSTEM >>>**
I würds garnit so kompliziert machen, es soll sich ja einigermaßen von jedem Spielertypen
spielen lassen, trotzdem solls a gewisse "Sucht" erzeugen, is nächste Level zu erreichen.
Schwierigkeit evtl exponentiell steigern? :>
#### Zwecks Schwierigkeit kein Problem, können ja Levelabhängige Konstanten erzeugen, die
#### z.b. den Enemies übergeben werden bzw. worauf die intern zugreifen über (Level.getCurrentLevel()).

**<<< HIGH-SCORE-System >>>**
SharedPreferences klingt fürn Anfang echt nice, Google-Konto Verknüpfung wär fürn Release 
dann natürlich da Hammer!
#### Google Verknüpfung hab i no nie gmacht. Würds uns aber ebenfalls für später aufbehalten, da
#### wir dafür die Google Game Services brauchen werden und des is sicher zusätzliche Arbeit (Erfolge, etc.).

**Bitte füg weitere Kategorien an, de ma umbedingt fürn ersten Release einbauen sollen!


****************************************************************************************************
**				**(2) Ergänzungen, spätere UpdatesReleases**			  **
****************************************************************************************************

1. Animationen
2. Barriers
...

****************************************************************************************************
**				**(3) Anregungen, Ideen**					  **
****************************************************************************************************

//empty

****************************************************************************************************
**				**(4) Kritikpunkte, Meinungen**					  **
****************************************************************************************************

//empty

