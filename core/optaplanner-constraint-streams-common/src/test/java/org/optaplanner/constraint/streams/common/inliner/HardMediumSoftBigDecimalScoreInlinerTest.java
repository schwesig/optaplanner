package org.optaplanner.constraint.streams.common.inliner;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.optaplanner.core.api.score.buildin.hardmediumsoftbigdecimal.HardMediumSoftBigDecimalScore;
import org.optaplanner.core.impl.domain.solution.descriptor.SolutionDescriptor;
import org.optaplanner.core.impl.testdata.domain.score.TestdataHardMediumSoftBigDecimalScoreSolution;

class HardMediumSoftBigDecimalScoreInlinerTest
        extends AbstractScoreInlinerTest<TestdataHardMediumSoftBigDecimalScoreSolution, HardMediumSoftBigDecimalScore> {

    @Test
    void defaultScore() {
        HardMediumSoftBigDecimalScoreInliner scoreInliner =
                new HardMediumSoftBigDecimalScoreInliner(constraintMatchEnabled);
        assertThat(scoreInliner.extractScore(0)).isEqualTo(HardMediumSoftBigDecimalScore.ZERO);
    }

    @Test
    void impactHard() {
        HardMediumSoftBigDecimalScoreInliner scoreInliner =
                new HardMediumSoftBigDecimalScoreInliner(constraintMatchEnabled);

        HardMediumSoftBigDecimalScore constraintWeight = HardMediumSoftBigDecimalScore.ofHard(BigDecimal.valueOf(90));
        WeightedScoreImpacter hardImpacter =
                scoreInliner.buildWeightedScoreImpacter(buildConstraint(constraintWeight), constraintWeight);
        UndoScoreImpacter undo1 = hardImpacter.impactScore(BigDecimal.ONE, JustificationsSupplier.empty());
        assertThat(scoreInliner.extractScore(0))
                .isEqualTo(HardMediumSoftBigDecimalScore.of(BigDecimal.valueOf(90), BigDecimal.ZERO, BigDecimal.ZERO));

        UndoScoreImpacter undo2 = hardImpacter.impactScore(BigDecimal.valueOf(2), JustificationsSupplier.empty());
        assertThat(scoreInliner.extractScore(0))
                .isEqualTo(HardMediumSoftBigDecimalScore.of(BigDecimal.valueOf(270), BigDecimal.ZERO, BigDecimal.ZERO));

        undo2.run();
        assertThat(scoreInliner.extractScore(0))
                .isEqualTo(HardMediumSoftBigDecimalScore.of(BigDecimal.valueOf(90), BigDecimal.ZERO, BigDecimal.ZERO));

        undo1.run();
        assertThat(scoreInliner.extractScore(0))
                .isEqualTo(HardMediumSoftBigDecimalScore.of(BigDecimal.valueOf(0), BigDecimal.ZERO, BigDecimal.ZERO));
    }

    @Test
    void impactMedium() {
        HardMediumSoftBigDecimalScoreInliner scoreInliner =
                new HardMediumSoftBigDecimalScoreInliner(constraintMatchEnabled);

        HardMediumSoftBigDecimalScore constraintWeight = HardMediumSoftBigDecimalScore.ofMedium(BigDecimal.valueOf(90));
        WeightedScoreImpacter hardImpacter =
                scoreInliner.buildWeightedScoreImpacter(buildConstraint(constraintWeight), constraintWeight);
        UndoScoreImpacter undo1 = hardImpacter.impactScore(BigDecimal.ONE, JustificationsSupplier.empty());
        assertThat(scoreInliner.extractScore(0))
                .isEqualTo(HardMediumSoftBigDecimalScore.of(BigDecimal.ZERO, BigDecimal.valueOf(90), BigDecimal.ZERO));

        UndoScoreImpacter undo2 = hardImpacter.impactScore(BigDecimal.valueOf(2), JustificationsSupplier.empty());
        assertThat(scoreInliner.extractScore(0))
                .isEqualTo(HardMediumSoftBigDecimalScore.of(BigDecimal.ZERO, BigDecimal.valueOf(270), BigDecimal.ZERO));

        undo2.run();
        assertThat(scoreInliner.extractScore(0))
                .isEqualTo(HardMediumSoftBigDecimalScore.of(BigDecimal.ZERO, BigDecimal.valueOf(90), BigDecimal.ZERO));

        undo1.run();
        assertThat(scoreInliner.extractScore(0))
                .isEqualTo(HardMediumSoftBigDecimalScore.of(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO));
    }

    @Test
    void impactSoft() {
        HardMediumSoftBigDecimalScoreInliner scoreInliner =
                new HardMediumSoftBigDecimalScoreInliner(constraintMatchEnabled);

        HardMediumSoftBigDecimalScore constraintWeight = HardMediumSoftBigDecimalScore.ofSoft(BigDecimal.valueOf(90));
        WeightedScoreImpacter hardImpacter =
                scoreInliner.buildWeightedScoreImpacter(buildConstraint(constraintWeight), constraintWeight);
        UndoScoreImpacter undo1 = hardImpacter.impactScore(BigDecimal.ONE, JustificationsSupplier.empty());
        assertThat(scoreInliner.extractScore(0))
                .isEqualTo(HardMediumSoftBigDecimalScore.of(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.valueOf(90)));

        UndoScoreImpacter undo2 = hardImpacter.impactScore(BigDecimal.valueOf(2), JustificationsSupplier.empty());
        assertThat(scoreInliner.extractScore(0))
                .isEqualTo(HardMediumSoftBigDecimalScore.of(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.valueOf(270)));

        undo2.run();
        assertThat(scoreInliner.extractScore(0))
                .isEqualTo(HardMediumSoftBigDecimalScore.of(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.valueOf(90)));

        undo1.run();
        assertThat(scoreInliner.extractScore(0))
                .isEqualTo(HardMediumSoftBigDecimalScore.of(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO));
    }

    @Test
    void impactAll() {
        HardMediumSoftBigDecimalScoreInliner scoreInliner =
                new HardMediumSoftBigDecimalScoreInliner(constraintMatchEnabled);

        HardMediumSoftBigDecimalScore constraintWeight = HardMediumSoftBigDecimalScore.of(
                BigDecimal.valueOf(10), BigDecimal.valueOf(100), BigDecimal.valueOf(1_000));
        WeightedScoreImpacter hardImpacter =
                scoreInliner.buildWeightedScoreImpacter(buildConstraint(constraintWeight), constraintWeight);
        UndoScoreImpacter undo1 = hardImpacter.impactScore(BigDecimal.TEN, JustificationsSupplier.empty());
        assertThat(scoreInliner.extractScore(0))
                .isEqualTo(HardMediumSoftBigDecimalScore.of(BigDecimal.valueOf(100), BigDecimal.valueOf(1_000),
                        BigDecimal.valueOf(10_000)));

        UndoScoreImpacter undo2 = hardImpacter.impactScore(BigDecimal.valueOf(20), JustificationsSupplier.empty());
        assertThat(scoreInliner.extractScore(0))
                .isEqualTo(HardMediumSoftBigDecimalScore.of(BigDecimal.valueOf(300), BigDecimal.valueOf(3_000),
                        BigDecimal.valueOf(30_000)));

        undo2.run();
        assertThat(scoreInliner.extractScore(0))
                .isEqualTo(HardMediumSoftBigDecimalScore.of(BigDecimal.valueOf(100), BigDecimal.valueOf(1_000),
                        BigDecimal.valueOf(10_000)));

        undo1.run();
        assertThat(scoreInliner.extractScore(0))
                .isEqualTo(HardMediumSoftBigDecimalScore.of(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO));
    }

    @Override
    protected SolutionDescriptor<TestdataHardMediumSoftBigDecimalScoreSolution> buildSolutionDescriptor() {
        return TestdataHardMediumSoftBigDecimalScoreSolution.buildSolutionDescriptor();
    }
}
