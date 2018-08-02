# Flying Fishes [![Maintenance](https://img.shields.io/badge/Maintained%3F-yes-green.svg)](https://GitHub.com/wsdt/Flying_Fishes/graphs/commit-activity) [![Generic badge](https://img.shields.io/badge/In-JAVA-RED.svg)](https://www.java.com/) [![GitHub license](https://img.shields.io/github/license/wsdt/Flying_Fishes.svg)](https://github.com/wsdt/Flying_Fishes/blob/master/LICENSE)

Native Android game which has been written as modular as possible. Consequently, new levels, worlds, enemies, backgrounds can be integrated into the game within a few minutes. Here you can jump to the [class-section](https://github.com/wsdt/Flying_Fishes/tree/master/YourOwnGame/app/src/main/java/yourowngame/com/yourowngame).

## Playstore
This app is currently only available to Alpha (Closed-Alpha track) and Beta (Open-Beta track) testers. 
- Playstore -> [Flying Fishes (https://play.google.com/store/apps/details?id=solution_wsdt.com.yourowngame.free)](https://play.google.com/store/apps/details?id=solution_wsdt.com.yourowngame.free)

_All (senseful) contributions/pull-requests will be examined and added to the next release (if you want so / if you don't just fork this repository)._

## Contribution [![Open Source Love svg2](https://badges.frapsoft.com/os/v2/open-source.svg?v=103)](https://github.com/ellerbrock/open-source-badges/) [![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square)](http://makeapullrequest.com)

This project is licensed under GNU V3, so contributions/pull-requests are welcome. All contributors get listed here. 

**Contributors** [![saythanks](https://img.shields.io/badge/say-thanks-ff69b4.svg)](https://saythanks.io/to/kennethreitz)
- Kevin Riedl ([WSDT](https://github.com/wsdt))
- Christof Jori ([SOLUTION](https://github.com/solution49)) 

### How to get started
This project has certain files, which have been added to the [.gitignore](https://github.com/wsdt/Flying_Fishes/blob/master/.gitignore) to avoid pushing specific secrets (e.g. advertisment tokens, etc.). As you wouldn't be able to compile/execute the app without those files we always add a so-called Template-File. Those template files are always named according to their original name + "\_TEMPLATE", so the file gets pushed. These files have most of the time a short explanation how you have to use/configure them. All template files are listed below (you have to configure all of them to execute the app): 

- IAdManager.java -> [Template-File](https://github.com/wsdt/Flying_Fishes/blob/master/YourOwnGame/app/src/main/java/yourowngame/com/yourowngame/classes/manager/interfaces/IAdManager_TEMPLATE.java)

To configure all template files above correctly, you just need to **copy** the template-file and then **rename** the **copied** file according the original name (e.g. IAdManager_TEMPLATE.java -> IAdManager.java). Additionally, you presumably should change some constants/lines in those newly created files (Reminder: also your new file won't get pushed at it is in the [.gitignore]((https://github.com/wsdt/Flying_Fishes/blob/master/.gitignore)). After that short configuration everything should run fine. Looking forward to your contribution :)!

### What is so special about this project?
1. This app hasn't been built with Unity or similar IDEs. We decided to code everything by ourself (though it might not the best solution, but yeah we wanted to know how it works). We would love to see you contributing!
1. Additionally, we tried to code as object-oriented and **generic** as possible. This means you could easily use this project as draft for another Arcade games. You would just need to change the Graphics, some params etc. and you would have a working game. 

### How to add an issue
1. **Add a good title to your issue.** Please use a concise and precise title. 
  * *BAD*: "ServiceMgr"
  * *GOOD*: "Redesign/Improve ServiceMgr"
2. **Add a good description to your issue.** Your description doesn't need to be concise, but should be clear/understandable and provide enough information for other contributors to solve the issue. For that I provided an issue template. 
