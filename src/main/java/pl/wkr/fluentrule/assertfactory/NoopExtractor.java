package pl.wkr.fluentrule.assertfactory;

class NoopExtractor implements ThrowableExtractor{

    @Override
    public Throwable extract(Throwable throwable) {
        return throwable;
    }
}
