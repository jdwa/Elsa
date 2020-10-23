package com.ldchotels.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ldchotels.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class AppFunctionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppFunction.class);
        AppFunction appFunction1 = new AppFunction();
        appFunction1.setId(1L);
        AppFunction appFunction2 = new AppFunction();
        appFunction2.setId(appFunction1.getId());
        assertThat(appFunction1).isEqualTo(appFunction2);
        appFunction2.setId(2L);
        assertThat(appFunction1).isNotEqualTo(appFunction2);
        appFunction1.setId(null);
        assertThat(appFunction1).isNotEqualTo(appFunction2);
    }
}
