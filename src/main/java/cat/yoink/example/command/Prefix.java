package cat.yoink.example.command;

import cat.yoink.clientapi.ClientAPI;
import cat.yoink.clientapi.command.Com;
import cat.yoink.clientapi.command.Command;
import cat.yoink.clientapi.util.LoggerUtil;

@Com(name = "Prefix", aliases = { "prefix", "pref", "px" }, usage = "prefix <character>")
public class Prefix extends Command
{
    @Override
    public void onRun(String arguments)
    {
        if (arguments.equals(""))
        {
            printUsage();
            return;
        }

        ClientAPI.setPrefix(arguments);
        LoggerUtil.sendMessage("Command prefix set to " + arguments);
    }
}
