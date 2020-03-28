package Controller;

import Model.Model;
import View.View;

public abstract class Controller {
    View view;
    Model model;

    public abstract void addView(View view);
    public abstract void addModel(Model model);
}
