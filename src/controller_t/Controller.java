package controller;

import model.Model;
import view.View;

public abstract class Controller {
    View view;
    Model model;

    public abstract void addView(View view);
    public abstract void addModel(Model model);
}
