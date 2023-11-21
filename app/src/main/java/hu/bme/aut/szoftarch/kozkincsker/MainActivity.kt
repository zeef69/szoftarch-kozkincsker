package hu.bme.aut.szoftarch.kozkincsker

import android.os.Bundle
import co.zsmb.rainbowcake.navigation.SimpleNavActivity
import co.zsmb.rainbowcake.navigation.navigator
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.szoftarch.kozkincsker.data.enums.TaskType
import hu.bme.aut.szoftarch.kozkincsker.data.model.Feedback
import hu.bme.aut.szoftarch.kozkincsker.data.model.Mission
import hu.bme.aut.szoftarch.kozkincsker.data.model.Session
import hu.bme.aut.szoftarch.kozkincsker.data.model.Task
import hu.bme.aut.szoftarch.kozkincsker.data.model.User
import hu.bme.aut.szoftarch.kozkincsker.ui.login.LoginFragment
import hu.bme.aut.szoftarch.kozkincsker.ui.main.MainFragment
import hu.bme.aut.szoftarch.kozkincsker.ui.mission_start.MissionStartFragment
import hu.bme.aut.szoftarch.kozkincsker.ui.new_mission.NewMissionFragment
import hu.bme.aut.szoftarch.kozkincsker.ui.new_task.NewTaskFragment
import hu.bme.aut.szoftarch.kozkincsker.ui.rating.RatingFragment
import hu.bme.aut.szoftarch.kozkincsker.views.NewTask

@AndroidEntryPoint
class MainActivity : SimpleNavActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            //navigator.add(LoginFragment())
            //navigator.add(MainFragment())
            //navigator.add(NewMissionFragment.newInstance())
            /*val session = Session()
            session.missionId = "OM41UfBlMQiHsG9qbV8u"
            navigator.add(RatingFragment.newInstance(session))*/
            navigator.add(MainFragment())
            var designer_teszt = User()
            designer_teszt.id = "HvMMIu5TGso7A1k7rqOc"
            designer_teszt.name = "reka_teszt"
            navigator.add(NewMissionFragment.newInstance(designer_teszt))
            /*val mission = Mission()
            mission.name = "Legjobb Mission"
            mission.description = "WOW Legjobb"
            mission.id = "1234"
            val feedback = Feedback()
            val feedback2 = Feedback()
            val feedback3 = Feedback()
            feedback.comment = "Legjobb"
            feedback.stars = 5.0
            feedback.writerId = "id1"
            feedback.missionId = mission.id
            feedback2.comment = "ÉLvezetes"
            feedback2.stars = 4.0
            feedback2.writerId = "id2"
            feedback3.missionId = mission.id
            feedback3.comment = "Hmmmm"
            feedback3.stars = 4.0
            feedback3.writerId = "id2"
            feedback3.missionId = mission.id
            mission.feedbackIds = mutableListOf(feedback,feedback2,feedback3)
            navigator.add(MissionStartFragment.newInstance(mission))*/
        }
    }
}