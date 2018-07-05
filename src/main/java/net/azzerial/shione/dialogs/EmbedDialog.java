package net.azzerial.shione.dialogs;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;

public abstract class EmbedDialog {

	protected final EventWaiter waiter; 
	protected Set<User> users;
	protected Set<Role> roles;
	protected final long timeout;
	protected final TimeUnit unit;
	
	protected EmbedDialog(EventWaiter waiter, Set<User> users, Set<Role> roles, long timeout, TimeUnit unit) {
		this.waiter = waiter;
		this.users = users;
		this.roles = roles;
		this.timeout = timeout;
		this.unit = unit;
	}
	
	public abstract void display(MessageChannel channel);
	
	public abstract void update(Message message);
	
	protected boolean canUserInteract(User user) {
		return (canUserInteract(user, null));
	}
	
	protected boolean canUserInteract(User user, Guild guild) {
		if (user.isBot()) {
			return (false);
		}
		if (users.isEmpty() && roles.isEmpty()) {
			return (true);
		}
		if (users.contains(user)) {
			return (true);
		}
		if (guild == null || !guild.isMember(user)) {
			return (false);
		}
		return (guild.getMember(user).getRoles().stream().anyMatch(roles :: contains));
	}
	
	@SuppressWarnings("unchecked")
	public abstract static class Builder<T extends Builder<T, V>, V extends EmbedDialog> {
		
		protected EventWaiter waiter; 
		protected Set<User> users = new HashSet<>();
		protected Set<Role> roles = new HashSet<>();
		protected long timeout = 1;
		protected TimeUnit unit = TimeUnit.MINUTES;
		
		public abstract V build();
		
		public final T setEventWaiter(EventWaiter waiter) {
			this.waiter = waiter;
			return ((T) this);
		}
		
		public final T addUsers(User... users) {
			this.users.addAll(Arrays.asList(users));
			return ((T) this);
		}
		
		public final T setUsers(User... users) {
			this.users.clear();
			this.users.addAll(Arrays.asList(users));
			return ((T) this);
		}
		
		public final T addRoles(Role... roles) {
			this.roles.addAll(Arrays.asList(roles));
			return ((T) this);
		}
		
		public final T setRoles(Role... roles) {
			this.roles.clear();
			this.roles.addAll(Arrays.asList(roles));
			return ((T) this);
		}
		
		public final T setTimeout(long timeout, TimeUnit unit) {
			this.timeout = timeout;
			this.unit = unit;
			return ((T) this);
		}
		
	}
}
