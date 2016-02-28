package ru.kadyrov.sync.data.context;

public enum Action {
    IMPORT {
        @Override
        public ActionHandler handler(ApplicationContext context) {
            return new ImportAction(context);
        }
    },
    EXPORT {
        @Override
        public ActionHandler handler(ApplicationContext context) {
            return new ExportAction(context);
        }
    };

    public abstract ActionHandler handler(ApplicationContext context);

}
