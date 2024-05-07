package at.htlklu.spring.api;

import org.springframework.web.servlet.ModelAndView;

public class ErrorsUtils
{
    public static ModelAndView get(String table, int id)
    {
        return show(String.format("Für die Id = %d (von %s) sind keine Daten vorhanden!", id, table));
    }

    public static ModelAndView show(String message)
    {
        ModelAndView mv = new ModelAndView();

        mv.setViewName("Error");
        mv.addObject("errorMessage", message);

        return mv;
    }

    public static String getErrorMessage(Exception e)
    {
        return e.getCause().getCause().getLocalizedMessage();
    }
}
