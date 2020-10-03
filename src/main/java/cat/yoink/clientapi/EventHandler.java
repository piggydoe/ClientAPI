package cat.yoink.clientapi;

import cat.yoink.clientapi.event.ChatMessageSendEvent;
import cat.yoink.clientapi.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class EventHandler
{
    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent event)
    {
        if (!Keyboard.getEventKeyState() || Keyboard.getEventKey() == Keyboard.KEY_NONE) return;

        for (Module module : ClientAPI.getModuleManager().getModules())
        {
            if (module.getBind() == Keyboard.getEventKey())
            {
                module.toggle();
            }
        }
    }

    @SubscribeEvent
    public void onChatSend(ClientChatEvent event)
    {
        if (event.getMessage().startsWith(ClientAPI.getPrefix()))
        {
            event.setCanceled(true);
            Minecraft.getMinecraft().ingameGUI.getChatGUI().addToSentMessages(event.getMessage());
            ClientAPI.getCommandManager().runCommand(event.getMessage());
            return;
        }

        ChatMessageSendEvent customEvent = new ChatMessageSendEvent(event.getMessage());
        MinecraftForge.EVENT_BUS.post(customEvent);

        if (customEvent.isCanceled())
        {
            event.setCanceled(true);
            return;
        }

        event.setMessage(customEvent.getMessage());
    }
}
