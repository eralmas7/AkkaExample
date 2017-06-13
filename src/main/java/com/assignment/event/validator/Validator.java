package com.assignment.event.validator;

/**
 * A validator to validate input event in the pipeline chain.
 */
public interface Validator {

    /**
     * Validate the given input.
     * 
     * @param input
     */
    public void validate(final String input);
}
