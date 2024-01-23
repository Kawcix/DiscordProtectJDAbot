package com.kawcix.suspicious_user;

public class Reaction {

    String user_id;
    String guild_id;
    Reaction_type reaction_type;
    String message;

    public enum Reaction_type {
        BAN,
        KICK
    }

    public static final class ReactionBuilder {
        private String user_id;
        private String guild_id;
        private Reaction_type reaction_type;
        private String message;

        ReactionBuilder() {
        }

        public ReactionBuilder user_id(String user_id) {
            this.user_id = user_id;
            return this;
        }

        public ReactionBuilder guild_id(String guild_id) {
            this.guild_id = guild_id;
            return this;
        }

        public ReactionBuilder reaction_type(Reaction_type reaction_type) {
            this.reaction_type = reaction_type;
            return this;
        }

        public ReactionBuilder message(String message) {
            this.message = message;
            return this;
        }

        public Reaction build() {
            Reaction reaction = new Reaction();
            reaction.user_id = this.user_id;
            reaction.reaction_type = this.reaction_type;
            reaction.message = this.message;
            reaction.guild_id = this.guild_id;
            return reaction;
        }
    }

}
