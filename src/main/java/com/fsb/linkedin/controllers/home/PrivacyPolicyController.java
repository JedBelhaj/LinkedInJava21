package com.fsb.linkedin.controllers.home;

import com.fsb.linkedin.utils.SceneSwitcher;
import javafx.scene.control.Button;

public class PrivacyPolicyController {
    public Button back;
    public void back() {
        SceneSwitcher.previous(back);
    }

}
