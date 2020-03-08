package com.data;

import org.jsoup.nodes.Document;
import org.junit.Test;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetElementsFromHtmlTest {
    @Test
    public void getElementsSuccessfullyWithHtmlPage() {
        final GetHtmlPage getHtmlPage = mock(GetHtmlPage.class);
        final Document document = mock(Document.class);
        when(getHtmlPage.process()).thenReturn(Optional.of(document));

    }
}
